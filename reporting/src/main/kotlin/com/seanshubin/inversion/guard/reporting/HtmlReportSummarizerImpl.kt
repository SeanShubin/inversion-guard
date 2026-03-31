package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.ClassAnalysisSummary
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateTextFileCommand
import com.seanshubin.inversion.guard.jvmspec.infrastructure.collections.Tree
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.output.formatting.JvmSpecFormat
import com.seanshubin.inversion.guard.reporting.HtmlElement.Companion.text
import com.seanshubin.inversion.guard.reporting.HtmlElement.Tag
import com.seanshubin.inversion.guard.reporting.HtmlElement.Text
import java.nio.charset.StandardCharsets
import java.nio.file.Path

class HtmlReportSummarizerImpl(
    private val baseDir: Path,
    private val outputDir: Path,
    private val reportGenerator: QualityMetricsDetailReportGenerator,
    private val jvmSpecFormat: JvmSpecFormat
) : HtmlReportSummarizer {
    private val classLoader = javaClass.classLoader

    override fun summarize(analysisList: List<ClassAnalysisSummary>): List<Command> {
        val detailReport = reportGenerator.generate(analysisList)
        val qualityMetrics = calculateQualityMetrics(analysisList)

        val classPathMap = analysisList.associate { analysis ->
            analysis.className to calculateDisassemblyPath(analysis.className, analysis.origin)
        }

        val indexHtml = generateIndexHtml(qualityMetrics, detailReport, classPathMap)
        val indexCommand =
            CreateTextFileCommand(outputDir.resolve(ReportCategory.BROWSE.directory).resolve("index.html"), indexHtml)

        val disassemblyIndexHtml = generateDisassemblyIndexHtml(analysisList, classPathMap)
        val disassemblyIndexCommand = CreateTextFileCommand(
            outputDir.resolve(ReportCategory.BROWSE.directory).resolve("disassembly.html"),
            disassemblyIndexHtml
        )

        val cssCommand = loadResourceAsCommand("quality-metrics.css")
        val redirectCommand = loadResourceAsCommand("_index.html")

        return listOf(indexCommand, disassemblyIndexCommand, cssCommand, redirectCommand)
    }

    private fun calculateQualityMetrics(analysisList: List<ClassAnalysisSummary>): QualityMetrics {
        val metricCounts = countByQualityMetric(analysisList)

        val inverted =
            metricCounts[com.seanshubin.inversion.guard.analysis.QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_INVERTED]
                ?: 0
        val acceptable =
            metricCounts[com.seanshubin.inversion.guard.analysis.QualityMetric.STATIC_INVOCATIONS_THAT_ARE_ACCEPTABLE]
                ?: 0
        val unclassified =
            metricCounts[com.seanshubin.inversion.guard.analysis.QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_CLASSIFIED]
                ?: 0

        return QualityMetrics(
            staticInvocationsThatShouldBeInverted = inverted,
            staticInvocationsThatAreAcceptable = acceptable,
            staticInvocationsThatShouldBeClassified = unclassified
        )
    }

    private fun countByQualityMetric(
        analysisList: List<ClassAnalysisSummary>
    ): Map<com.seanshubin.inversion.guard.analysis.QualityMetric, Int> {
        return analysisList
            .flatMap { it.methodAnalysisList }
            .filter { !it.isBoundaryLogic() }
            .flatMap { it.staticInvocations }
            .groupingBy { com.seanshubin.inversion.guard.analysis.mapToQualityMetric(it.invocationTypes) }
            .eachCount()
    }

    override fun generateClassPage(analysis: ClassAnalysis): Command {
        return generateClassDisassemblyPage(analysis)
    }

    private fun generateIndexHtml(
        qualityMetrics: QualityMetrics,
        detailReport: QualityMetricsDetailReport,
        classPathMap: Map<String, Path>
    ): String {
        val html = Tag(
            "html",
            attributes = listOf("lang" to "en"),
            children = listOf(
                createHead(),
                createBody(qualityMetrics, detailReport, classPathMap)
            )
        )
        val doctype = "<!DOCTYPE html>"
        val htmlLines = html.toLines()
        return (listOf(doctype) + htmlLines).joinToString("\n")
    }

    private fun generateDisassemblyIndexHtml(
        analysisList: List<ClassAnalysisSummary>,
        classPathMap: Map<String, Path>
    ): String {
        val sortedAnalyses = analysisList.sortedBy { baseDir.relativize(it.origin).toString() }

        val classListItems = sortedAnalyses.map { analysis ->
            val className = analysis.className
            val origin = baseDir.relativize(analysis.origin).toString()
            val relativePath = classPathMap[className]?.toString() ?: "$className-disassembly.html"

            Tag(
                "li",
                Tag(
                    "a",
                    attributes = listOf("href" to relativePath, "class" to "class-link"),
                    children = listOf(Text(origin))
                )
            )
        }

        val html = Tag(
            "html",
            attributes = listOf("lang" to "en"),
            children = listOf(
                createDisassemblyIndexHead(),
                createDisassemblyIndexBody(classListItems)
            )
        )

        val doctype = "<!DOCTYPE html>"
        val htmlLines = html.toLines()
        return (listOf(doctype) + htmlLines).joinToString("\n")
    }

    private fun createDisassemblyIndexHead(): HtmlElement {
        return Tag(
            "head",
            Tag("meta", attributes = listOf("charset" to "UTF-8")),
            Tag(
                "meta",
                attributes = listOf("name" to "viewport", "content" to "width=device-width, initial-scale=1.0")
            ),
            text("title", "Disassembly Index"),
            Tag("link", attributes = listOf("rel" to "stylesheet", "href" to "quality-metrics.css"))
        )
    }

    private fun createDisassemblyIndexBody(classListItems: List<HtmlElement>): HtmlElement {
        return Tag(
            "body",
            text("h1", "Disassembly Index"),
            Tag(
                "nav",
                attributes = listOf("class" to "back-navigation"),
                children = listOf(
                    Tag(
                        "a",
                        attributes = listOf("href" to "index.html"),
                        children = listOf(Text("← Back to Quality Metrics Report"))
                    )
                )
            ),
            Tag(
                "section",
                text("h2", "All Classes"),
                Tag(
                    "ul",
                    attributes = listOf("class" to "disassembly-index-list"),
                    children = classListItems
                )
            )
        )
    }

    private fun createHead(): HtmlElement {
        return Tag(
            "head",
            Tag("meta", attributes = listOf("charset" to "UTF-8")),
            Tag(
                "meta",
                attributes = listOf("name" to "viewport", "content" to "width=device-width, initial-scale=1.0")
            ),
            text("title", "Quality Metrics Report"),
            Tag("link", attributes = listOf("rel" to "stylesheet", "href" to "quality-metrics.css"))
        )
    }

    private fun createBody(
        qualityMetrics: QualityMetrics,
        detailReport: QualityMetricsDetailReport,
        classPathMap: Map<String, Path>
    ): HtmlElement {
        return Tag(
            "body",
            text("h1", "Quality Metrics Report"),
            createTableOfContents(),
            createQualityMetricsSection(qualityMetrics),
            createStaticInvocationDetailSection(detailReport, classPathMap)
        )
    }

    private fun createTableOfContents(): HtmlElement {
        return Tag(
            "nav",
            text("h2", "Table of Contents"),
            Tag(
                "ul",
                Tag(
                    "li",
                    Tag(
                        "a",
                        attributes = listOf("href" to "#quality-metrics"),
                        children = listOf(Text("Quality Metrics"))
                    )
                ),
                Tag(
                    "li",
                    Tag(
                        "a",
                        attributes = listOf("href" to "filters.html"),
                        children = listOf(Text("Filter Statistics"))
                    )
                ),
                Tag(
                    "li",
                    Tag(
                        "a",
                        attributes = listOf("href" to "disassembly.html"),
                        children = listOf(Text("Disassembly Index"))
                    )
                )
            )
        )
    }

    private fun createQualityMetricsSection(
        qualityMetrics: QualityMetrics
    ): HtmlElement {
        return Tag(
            "section",
            attributes = listOf("id" to "quality-metrics"),
            children = listOf(
                text("h2", "Quality Metrics"),
                createSummaryTable(qualityMetrics)
            )
        )
    }

    private fun createStaticInvocationDetailSection(
        detailReport: QualityMetricsDetailReport,
        classPathMap: Map<String, Path>
    ): HtmlElement {
        return Tag(
            "section",
            attributes = listOf("id" to "static-invocation-detail"),
            children = listOf(
                text("h2", "Static Invocation Detail"),
                createDetailTable(detailReport, classPathMap)
            )
        )
    }

    private fun createSummaryTable(qualityMetrics: QualityMetrics): HtmlElement {
        val invertedCssClass =
            if (qualityMetrics.staticInvocationsThatShouldBeInverted > 0) "has-problems" else "no-problems"
        val unclassifiedCssClass =
            if (qualityMetrics.staticInvocationsThatShouldBeClassified > 0) "has-warnings" else "no-problems"

        return Tag(
            "table",
            attributes = listOf("class" to "summary-table"),
            children = listOf(
                Tag(
                    "thead",
                    Tag(
                        "tr",
                        text("th", "Metric"),
                        text("th", "Value")
                    )
                ),
                Tag(
                    "tbody",
                    Tag(
                        "tr",
                        text("td", "staticInvocationsThatShouldBeInverted"),
                        Tag(
                            "td",
                            attributes = listOf("class" to "metric-value $invertedCssClass"),
                            children = listOf(Text(qualityMetrics.staticInvocationsThatShouldBeInverted.toString()))
                        )
                    ),
                    Tag(
                        "tr",
                        text("td", "staticInvocationsThatAreAcceptable"),
                        Tag(
                            "td",
                            attributes = listOf("class" to "metric-value no-problems"),
                            children = listOf(Text(qualityMetrics.staticInvocationsThatAreAcceptable.toString()))
                        )
                    ),
                    Tag(
                        "tr",
                        text("td", "staticInvocationsThatShouldBeClassified"),
                        Tag(
                            "td",
                            attributes = listOf("class" to "metric-value $unclassifiedCssClass"),
                            children = listOf(Text(qualityMetrics.staticInvocationsThatShouldBeClassified.toString()))
                        )
                    )
                )
            )
        )
    }

    private fun createDetailTable(
        detailReport: QualityMetricsDetailReport,
        classPathMap: Map<String, Path>
    ): HtmlElement {
        if (detailReport.classes.isEmpty()) {
            return Tag("p", Text("No quality metric violations found."))
        }

        val rows = detailReport.classes.flatMap { classDetail ->
            createClassRows(classDetail, classPathMap)
        }

        return Tag(
            "table",
            attributes = listOf("class" to "detail-table"),
            children = listOf(
                createDetailTableHeader(),
                Tag("tbody", rows)
            )
        )
    }

    private fun createDetailTableHeader(): HtmlElement {
        return Tag(
            "thead",
            Tag(
                "tr",
                text("th", "Level"),
                Tag(
                    "th",
                    attributes = listOf("class" to "signature-column"),
                    children = listOf(Text("Name/Signature"))
                ),
                text("th", "Problem Count"),
                text("th", "Complexity"),
                text("th", "Details")
            )
        )
    }

    private fun createClassRows(
        classDetail: QualityMetricsClassDetail,
        classPathMap: Map<String, Path>
    ): List<HtmlElement> {
        val relativePath =
            classPathMap[classDetail.className]?.toString() ?: "${classDetail.className}-disassembly.html"

        val classRow = Tag(
            "tr",
            attributes = listOf("class" to "class-row"),
            children = listOf(
                text("td", "Class"),
                Tag(
                    "td",
                    attributes = listOf("class" to "class-name signature-column"),
                    children = listOf(
                        Tag(
                            "a",
                            attributes = listOf("href" to relativePath),
                            children = listOf(Text(classDetail.className))
                        )
                    )
                ),
                Tag(
                    "td",
                    attributes = listOf("class" to "problem-count"),
                    children = listOf(Text(classDetail.problemCount.toString()))
                ),
                text("td", classDetail.complexity.toString()),
                text("td", "${classDetail.methods.size} method(s)")
            )
        )

        val methodRows = classDetail.methods.flatMap { methodDetail ->
            createMethodRows(methodDetail)
        }

        return listOf(classRow) + methodRows
    }

    private fun createMethodRows(methodDetail: QualityMetricsMethodDetail): List<HtmlElement> {
        val methodRow = Tag(
            "tr",
            attributes = listOf("class" to "method-row"),
            children = listOf(
                text("td", "Method"),
                Tag(
                    "td",
                    attributes = listOf("class" to "method-signature signature-column"),
                    children = listOf(Text(methodDetail.methodSignature))
                ),
                text("td", ""),
                text("td", methodDetail.complexity.toString()),
                text("td", "${methodDetail.invocations.size} invocation(s)")
            )
        )

        val invocationRows = methodDetail.invocations.map { invocationDetail ->
            createInvocationRow(invocationDetail)
        }

        return listOf(methodRow) + invocationRows
    }

    private fun createInvocationRow(invocationDetail: QualityMetricsInvocationDetail): HtmlElement {
        val typeClass = "invocation-type-${invocationDetail.invocationType.lowercase()}"
        return Tag(
            "tr",
            attributes = listOf("class" to "invocation-row $typeClass"),
            children = listOf(
                text("td", "Invocation"),
                Tag(
                    "td",
                    attributes = listOf("class" to "invocation-signature signature-column"),
                    children = listOf(Text(invocationDetail.signature))
                ),
                text("td", ""),
                text("td", ""),
                Tag(
                    "td",
                    attributes = listOf("class" to "invocation-details"),
                    children = listOf(Text("${invocationDetail.invocationType} ${invocationDetail.opcode}"))
                )
            )
        )
    }

    private fun loadResourceAsCommand(resourceName: String): Command {
        val resourcePath = "html-report/$resourceName"
        val inputStream = classLoader.getResourceAsStream(resourcePath)
            ?: throw RuntimeException("Unable to load resource: $resourcePath")
        val content = inputStream.bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
        val path = outputDir.resolve(ReportCategory.BROWSE.directory).resolve(resourceName)
        return CreateTextFileCommand(path, content)
    }

    private fun calculateDisassemblyPath(jvmClass: JvmClass): Path {
        return calculateDisassemblyPath(jvmClass.thisClassName, jvmClass.origin)
    }

    private fun calculateDisassemblyPath(className: String, origin: Path): Path {
        val relativePath = baseDir.relativize(origin).parent
        val classFileName = origin.fileName.toString().removeSuffix(".class")
        return relativePath.resolve("$classFileName-disassembly.html")
    }

    private fun calculateCssRelativePath(htmlPath: Path): String {
        val depth = htmlPath.nameCount - 1 // -1 because we don't count the filename itself
        return if (depth == 0) {
            "quality-metrics.css"
        } else {
            "../".repeat(depth) + "quality-metrics.css"
        }
    }

    private fun treeToHtml(tree: Tree): HtmlElement {
        return if (tree.children.isEmpty()) {
            Tag("div", attributes = listOf("class" to "tree-leaf"), children = listOf(Text(tree.node)))
        } else {
            Tag(
                "details",
                Tag("summary", Text(tree.node)),
                *tree.children.map { treeToHtml(it) }.toTypedArray()
            )
        }
    }

    private fun generateClassDisassemblyPage(analysis: ClassAnalysis): Command {
        val trees = jvmSpecFormat.classTreeList(analysis.jvmClass)
        val relativePath = calculateDisassemblyPath(analysis.jvmClass)
        val cssPath = calculateCssRelativePath(relativePath)
        val indexPath = calculateCssRelativePath(relativePath).replace("quality-metrics.css", "index.html")
        val textFileName = relativePath.fileName.toString().replace("-disassembly.html", "-disassembled.txt")
        val html =
            generateClassDisassemblyHtml(analysis.jvmClass.thisClassName, trees, cssPath, indexPath, textFileName)

        val path = outputDir.resolve(ReportCategory.BROWSE.directory).resolve(relativePath)
        return CreateTextFileCommand(path, html)
    }

    private fun generateClassDisassemblyHtml(
        className: String,
        trees: List<Tree>,
        cssPath: String,
        indexPath: String,
        textFilePath: String
    ): String {
        val html = Tag(
            "html",
            attributes = listOf("lang" to "en"),
            children = listOf(
                createClassPageHead(className, cssPath),
                createClassPageBody(className, trees, indexPath, textFilePath)
            )
        )
        val doctype = "<!DOCTYPE html>"
        val htmlLines = html.toLines()
        return (listOf(doctype) + htmlLines).joinToString("\n")
    }

    private fun createClassPageHead(className: String, cssPath: String): HtmlElement {
        return Tag(
            "head",
            Tag("meta", attributes = listOf("charset" to "UTF-8")),
            Tag(
                "meta",
                attributes = listOf("name" to "viewport", "content" to "width=device-width, initial-scale=1.0")
            ),
            text("title", "Disassembly: $className"),
            Tag("link", attributes = listOf("rel" to "stylesheet", "href" to cssPath))
        )
    }

    private fun createClassPageBody(
        className: String,
        trees: List<Tree>,
        indexPath: String,
        textFilePath: String
    ): HtmlElement {
        val treeElements = trees.map { treeToHtml(it) }

        return Tag(
            "body",
            text("h1", "Class Disassembly: $className"),
            createBackLink(indexPath),
            createTextFileLink(textFilePath),
            Tag(
                "section",
                attributes = listOf("id" to "disassembly", "class" to "disassembly-section"),
                children = treeElements
            )
        )
    }

    private fun createBackLink(indexPath: String): HtmlElement {
        return Tag(
            "nav",
            attributes = listOf("class" to "back-navigation"),
            children = listOf(
                Tag(
                    "a",
                    attributes = listOf("href" to indexPath),
                    children = listOf(Text("← Back to Quality Metrics Report"))
                )
            )
        )
    }

    private fun createTextFileLink(textFilePath: String): HtmlElement {
        return Tag(
            "nav",
            attributes = listOf("class" to "back-navigation"),
            children = listOf(
                Tag(
                    "a",
                    attributes = listOf("href" to textFilePath),
                    children = listOf(Text("View Raw Text Disassembly"))
                )
            )
        )
    }
}
