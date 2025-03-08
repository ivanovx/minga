package pro.ivanov

import io.ktor.server.cio.*
import io.ktor.server.engine.*

import pro.ivanov.services.ContentIndexer

fun main(args: Array<String>) {
    ContentIndexer.instance.indexContent();

    embeddedServer(CIO, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureRouting()
}