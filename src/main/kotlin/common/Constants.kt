package pro.ivanov.common

import java.nio.file.Path
import kotlin.io.path.Path

class Constants {
    companion object {
        private val contentRootEnv = System.getenv("CONTENT_ROOT").orEmpty()

        val contentRoot = Path(contentRootEnv)

        val articlesRoot: Path = contentRoot.resolve("articles")

        val pagesRoot: Path = contentRoot.resolve("pages")
    }
}