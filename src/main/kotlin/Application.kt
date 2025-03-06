package pro.ivanov

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import pro.ivanov.services.ContentIndexer

fun main(args: Array<String>) {
    ContentIndexer.instance.indexContent();

    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}