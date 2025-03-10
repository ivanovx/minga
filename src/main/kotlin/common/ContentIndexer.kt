package pro.ivanov.common

import pro.ivanov.models.Article

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.pathString

class ContentIndexer private constructor() {
    private val rootDir: String = Constants.contentRoot

    lateinit var articles: List<Article> private set;

    companion object {
        val instance: ContentIndexer by lazy { ContentIndexer() }
    }

    fun indexContent() {
        println("Begin index articles");

        val articles = Path(rootDir)
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

        this.articles = articles


        println("Success index ${this.articles.size} articles")
    }
}