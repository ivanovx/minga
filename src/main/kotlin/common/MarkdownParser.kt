package pro.ivanov.common

import org.commonmark.Extension
import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

class MarkdownParser private constructor() {
    data class MarkdownParserResult(
        val header: Map<String, List<String>>,
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

            println(visitor.data)

            val result = MarkdownParserResult(visitor.data, renderer.render(document))

            return result;
        }
    }
}