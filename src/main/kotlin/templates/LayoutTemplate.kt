package pro.ivanov.templates

import io.ktor.server.html.*
import kotlinx.html.*

import pro.ivanov.services.ArticleService

class LayoutTemplate: Template<HTML> {
    val content = Placeholder<FlowContent>()

    private val tags = ArticleService().getTags()

    override fun HTML.apply() {
        head {
            meta(charset = "UTF-8")
            meta("viewport", "width=device-width, initial-scale=1")

            styleLink("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css")

            title { +"Template Layout" }
        }
        body(classes = "container bg-body-tertiary") {
            nav(classes = "navbar sticky-top navbar-expand-lg bg-primary-subtle") {
                div(classes = "container-fluid") {
                    a(classes = "navbar-brand", href = "/") {
                        + "Downr"
                    }
                    button(classes = "navbar-toggler", type = ButtonType.button) {
                        attributes.put("data-bs-toggle", "collapse")
                        attributes.put("data-bs-target", "#navbarSupportedContent")

                        span(classes = "navbar-toggler-icon") {}
                    }
                    div(classes = "collapse navbar-collapse") {
                        id = "navbarSupportedContent"
                        ul(classes = "navbar-nav me-auto mb-2 mb-lg-0") {
                            li(classes = "nav-item") {
                                a(classes = "nav-link", href = "/") { +"Home" }
                            }
                            li(classes = "nav-item") {
                                a(classes = "nav-link", href = "/articles") { +"Articles" }
                            }
                            li(classes="nav-item dropdown") {
                                a(classes = "nav-link dropdown-toggle", href = "#") {
                                    attributes.put("data-bs-toggle", "dropdown")
                                    + "Tags"
                                }
                                ul(classes = "dropdown-menu") {
                                    tags.forEach {
                                        li {
                                            a(classes = "dropdown-item", href="") {
                                                + it
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            div(classes = "my-5") {
                insert(content)
            }

            script(src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"){}
        }
    }
}