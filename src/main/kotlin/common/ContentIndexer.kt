package pro.ivanov.common

import pro.ivanov.models.Article
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.pathString


class ContentIndexer private constructor() {
    private val watchService = FileSystems.getDefault().newWatchService()

    lateinit var articles: List<Article> private set;

    companion object {
        val instance: ContentIndexer by lazy { ContentIndexer() }
    }

    fun indexContent() {
        val rootPath = Constants.contentRoot
        val articlesPath = Constants.articlesRoot
        val pagesPath = Constants.pagesRoot

        println("Begin index articles");

        rootPath.register(watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY);

        this.articles = indexArticles(articlesPath.pathString)

        println("Success index ${this.articles.size} articles")
    }

    fun watchContent() {
        println("Watch content");

        while (true) {
            val key = watchService.take()

            for (event in key.pollEvents()) {
                // Handle the specific event
                if (event.kind() === StandardWatchEventKinds.ENTRY_CREATE) {
                    println("File created: " + event.context())
                } else if (event.kind() === StandardWatchEventKinds.ENTRY_DELETE) {
                    println("File deleted: " + event.context())
                } else if (event.kind() === StandardWatchEventKinds.ENTRY_MODIFY) {
                    println("File modified: " + event.context())
                }
            }

            // To receive further events, reset the key
            key.reset()
        }
    }

    private fun indexArticles(rootPath: String): List<Article> {
        val articles = Path(rootPath)
            .listDirectoryEntries()
            .filter { it.isDirectory() }
            .map {
                val indexFile = Path(it.pathString, "index.md")
                val bufferedReader = File(indexFile.pathString).bufferedReader()
                val rawContent = bufferedReader.use { it.readText() }
                val markdown = MarkdownParser(rawContent).parse()

                val article = Article(
                    title = markdown.metadata.title,
                    slug = markdown.metadata.slug,
                    date = LocalDate.parse(markdown.metadata.date, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    tags = markdown.metadata.tags,
                    content = markdown.content
                )

                article
            }

        return articles
    }
}