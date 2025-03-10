package pro.ivanov.templates

import io.ktor.server.html.*
import kotlinx.html.*

class ArticleTemplate: Template<FlowContent> {
    val header = Placeholder<FlowContent>()
    val content = Placeholder<Unsafe>()

    override fun FlowContent.apply() {
        article {
            h2 {
                insert(header)
            }
            unsafe {
                insert(content)
            }
        }
    }
}