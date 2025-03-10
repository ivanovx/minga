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

        val rawArticles = Path(rootDir)
            .listDirectoryEntries()
            .filter { it.isDirectory() }
            .map {
                val indexFile = Path(it.pathString, "index.md")
                val bufferedReader = File(indexFile.pathString).bufferedReader()
                val rawContent = bufferedReader.use { it.readText() }
                val content = MarkdownParser.parse(rawContent);

                content
            }

        this.articles = rawArticles.map {
            val title: String = it.header.get("title")?.first().toString()
            val slug: String = it.header.get("slug")?.first().toString()
            val date: String = it.header.get("date")?.first().toString()
            val tags: List<String> = it.header.get("tags")?.toMutableList() ?: listOf("unknown")

            val aricle = Article(
                title,
                slug,
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                tags,
                it.content
            )

            aricle
        }

        println("Success index ${this.articles.size} articles");
    }
}