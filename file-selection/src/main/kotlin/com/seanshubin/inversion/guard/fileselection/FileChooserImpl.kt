package com.seanshubin.inversion.guard.fileselection

import com.seanshubin.inversion.guard.di.contract.FilesContract
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class FileChooserImpl(
    private val files: FilesContract,
    private val notify: FileSelectionNotify? = null
) : FileChooser {
    override fun choose(fileSelection: FileSelection): List<Path> {
        val results = mutableListOf<Path>()
        val visitor = createVisitor(fileSelection, results)
        files.walkFileTree(fileSelection.baseDir, visitor)
        return results.sortedBy { it.toString() }
    }

    private fun createVisitor(fileSelection: FileSelection, results: MutableList<Path>): SimpleFileVisitor<Path> {
        return object : SimpleFileVisitor<Path>() {
            override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                if (dir == fileSelection.baseDir) return FileVisitResult.CONTINUE
                val relative = normalize(fileSelection.baseDir.relativize(dir))
                val matchedSkip = fileSelection.skipDirectoryPatterns.firstOrNull { Regex(it).matches(relative) }
                return if (matchedSkip != null) {
                    notify?.onSkipDirectory(relative, matchedSkip)
                    FileVisitResult.SKIP_SUBTREE
                } else {
                    FileVisitResult.CONTINUE
                }
            }

            override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                val relative = normalize(fileSelection.baseDir.relativize(file))
                val matchedInclude = fileSelection.includePatterns.firstOrNull { Regex(it).matches(relative) }
                if (matchedInclude != null) {
                    val matchedExclude = fileSelection.excludePatterns.firstOrNull { Regex(it).matches(relative) }
                    if (matchedExclude != null) {
                        notify?.onExclude(relative, matchedExclude)
                    } else {
                        results.add(file)
                        notify?.onInclude(relative, matchedInclude)
                    }
                } else {
                    notify?.onUnmatched(relative)
                }
                return FileVisitResult.CONTINUE
            }
        }
    }

    private fun normalize(path: Path): String = path.toString().replace('\\', '/')
}
