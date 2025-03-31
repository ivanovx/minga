package pro.ivanov

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

import pro.ivanov.common.ContentIndexer

suspend fun indexContent() {
    ContentIndexer.instance.indexContent()
    ContentIndexer.instance.watchContent()
}

suspend fun serveContent() {
    embeddedServer(CIO, port = 8080) {
        configureRouting()
    }.start(wait = true)
}

suspend fun main(args: Array<String>) {
    coroutineScope {
        async { indexContent() }
        async { serveContent() }
    }
}