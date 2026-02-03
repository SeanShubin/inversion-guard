package com.seanshubin.inversion.guard.fileselection

import com.seanshubin.inversion.guard.jvmspec.analysis.filtering.Filter
import java.nio.file.Path

class FileSelectorFileVisitorFactoryImpl(
    private val directoryFilter: Filter,
    private val classFileNameFilter: Filter
) : FileSelectorFileVisitorFactory {
    override fun <T> create(
        process: (Path) -> List<T>,
        results: MutableList<T>
    ): FileSelectorFileVisitor<T> {
        return FileSelectorFileVisitor(
            directoryFilter,
            classFileNameFilter,
            process,
            results
        )
    }
}
