package pro.ivanov

import io.ktor.server.cio.*
import io.ktor.server.engine.*

import pro.ivanov.common.ContentIndexer

fun main(args: Array<String>) {
    ContentIndexer.instance.indexContent();

    embeddedServer(CIO, port = 8080) {
        configureRouting()
    }.start(wait = true)
}