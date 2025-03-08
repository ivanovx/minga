package pro.ivanov

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.html.*

import pro.ivanov.services.ContentIndexer

fun Application.configureRouting() {
    val articles = ContentIndexer.instance.articles

    routing {
        get("/") {
           call.respondHtml(HttpStatusCode.OK) {
                head {
                    title("Downr")
                }
                body {
                    ol {
                        articles.forEach {
                            this.li {
                                a("/${it.slug}") {
                                    + it.title
                                }
                            }
                        }
                    }
                }
           }
        }
        get("/{slug}") {
            val slug = call.parameters["slug"]
            val article = articles.find { it.slug == slug }

            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title(article?.title ?: "")
                }
                body {
                    h1 {
                        + article?.title!! ?: ""
                    }
                    unsafe {
                        raw(article?.content!! ?: "")
                    }
                }
            }
        }
    }
}
