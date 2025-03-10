package pro.ivanov.templates

import io.ktor.server.html.*
import kotlinx.html.*

class ArticleTemplate: Template<FlowContent> {
    val articleTitle = Placeholder<FlowContent>()
    val articleDate = Placeholder<FlowContent>()
    val articleContent = Placeholder<Unsafe>()

    override fun FlowContent.apply() {
        article(classes = "card") {
            div(classes = "card-body") {
                h2("card-title") {
                    insert(articleTitle)
                }
                h5("card-subtitle") {
                    insert(articleDate)
                }
                div(classes = "mt-3") {
                    unsafe {
                        insert(articleContent)
                    }
                }
            }
        }
        styleLink("https://cdnjs.cloudflare.com/ajax/libs/prism/9000.0.1/themes/prism.min.css")
        script(src = "https://cdnjs.cloudflare.com/ajax/libs/prism/9000.0.1/prism.min.js") {}
    }
}