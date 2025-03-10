package pro.ivanov.templates

import io.ktor.server.html.*
import kotlinx.html.*

class LayoutTemplate: Template<HTML> {
    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        head {
            meta(charset = "UTF-8")
            meta("viewport", "width=device-width, initial-scale=1")

            styleLink("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css")

            title { +"Template Layout" }
        }
        body {
            div {
                classes = setOf("container")
                insert(content)
            }

            scriptLink("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js")
        }
    }
}