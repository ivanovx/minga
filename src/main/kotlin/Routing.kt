package pro.ivanov

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.html.*

import pro.ivanov.services.ArticleService
import pro.ivanov.templates.ArticleTemplate
import pro.ivanov.templates.LayoutTemplate

import java.time.format.DateTimeFormatter

fun Application.configureRouting() {
    routing {
        get("/") {
            val articles = ArticleService().getArticles()

            call.respondHtmlTemplate(LayoutTemplate()) {
                content {
                    div(classes = "row") {
                        articles.forEach {
                            div(classes = "col-4 mt-2 mb-2") {
                                div(classes = "card text-center") {
                                    div(classes = "card-header") {
                                        a("/${it.slug}", classes = "card-text") {
                                            +it.title
                                        }
                                    }
                                    div(classes = "card-body") {
                                        + "..."
                                    }
                                    div(classes = "card-footer") {
                                        +it.date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        get("/{slug}") {
            val slug = call.parameters["slug"]!!
            val article = ArticleService().getArticle(slug)

            call.respondHtmlTemplate(LayoutTemplate()) {
                content {
                    insert(ArticleTemplate()) {
                        header {
                            +article.title
                        }
                        content {
                            +article.content
                        }
                    }
                }
            }
        }
    }
}
