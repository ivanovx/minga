package pro.ivanov.common

import org.commonmark.Extension
import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class MarkdownParser private constructor() {
    data class MarkdownParserResult(
        val header: Map<String, List<String>>, // metadata
        val content: String,
    )

    companion object {
        fun parse(markdown: String): MarkdownParserResult {
            val visitor = YamlFrontMatterVisitor()

            val extensions: List<Extension> = listOf(
                TablesExtension.create(),
                YamlFrontMatterExtension.create()
            )

            val parser: Parser = Parser.builder().extensions(extensions).build()
            val document: Node = parser.parse(markdown)

            document.accept(visitor)

            val renderer = HtmlRenderer.builder().build()
            val html = renderer.render(document)
            val doc: Document = Jsoup.parse(html)

            val slug = visitor.data.get("slug")?.first()?.toString()

            doc.select("img").forEach {
                val src = it.attr("src").replace("media/", "/content/${slug}/media/")

                it.attr("src", src)
                it.addClass("img-fluid")
            }

            val result = MarkdownParserResult(visitor.data, doc.body().outerHtml())

            return result;
        }
    }
}