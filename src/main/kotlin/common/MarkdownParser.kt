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
import pro.ivanov.models.Markdown
import pro.ivanov.models.Metadata

class MarkdownParser(
    private val markdown: String
) {
    fun parse(): Markdown {
        val visitor = YamlFrontMatterVisitor()

        val extensions: List<Extension> = listOf(
            TablesExtension.create(),
            YamlFrontMatterExtension.create()
        )

        val parser: Parser = Parser
            .builder()
            .extensions(extensions)
            .build()

        val document: Node = parser.parse(this.markdown)

        document.accept(visitor)

        val renderer = HtmlRenderer.builder().build()
        val html = renderer.render(document)
        val doc: Document = Jsoup.parse(html)

        val slug = visitor.data.get("slug")?.first()?.toString()

        doc.select("img").forEach {
            val src = it.attr("src").replace("media/", "/content/articles/${slug}/media/")

            it.attr("src", src)
            it.addClass("img-fluid")
        }

        val content = doc.body().outerHtml()

        val metadata = this.parseMetadata(visitor.data)
        val markdown = Markdown(metadata, content)

        return markdown;
    }

    private fun parseMetadata(data: Map<String, List<String>>): Metadata {
        val title = data.get("title")?.first()?.toString()
        val slug = data.get("slug")?.first()?.toString()
        val date = data.get("date")?.first()?.toString()
        val tags = data.get("tags")?.flatMap { it.split(",") }

        val metadata = Metadata(
            title!!,
            slug!!,
            date!!,
            tags!!,
        )

        return metadata;
    }
}