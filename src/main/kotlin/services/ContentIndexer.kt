package pro.ivanov.services

import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.FolderMetadata
import pro.ivanov.common.MarkdownParser
import pro.ivanov.common.DropboxClient
import pro.ivanov.models.Article
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

class ContentIndexer private constructor() {
    private var dropboxClient: DropboxClient = DropboxClient()

    lateinit var articles: List<Article> private set;

    companion object {
        val instance: ContentIndexer by lazy { ContentIndexer() }
    }

    fun indexContent() {
        println("Begin index articles");

        val client = dropboxClient.getClient()
        val folderContent = client.files().listFolder("/downr").entries.toList()
        val folders = folderContent.filterIsInstance<FolderMetadata>()

        val filesInFolder = folders.flatMap {
            val content = client.files().listFolder("${it.pathLower}").entries.toList()
            val files = content.filterIsInstance<FileMetadata>().toList()

            files;
        }

        val indexFiles = filesInFolder.filter { it.name.equals("index.md") }

        val indexFilesContent = indexFiles.map {
            val fileInputStream = client.files().download(it.pathLower).inputStream
            val fileContent = BufferedReader(InputStreamReader(fileInputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"))

            val content = MarkdownParser.parse(fileContent);

            content
        }

        this.articles = indexFilesContent.map {
            val title: String = it.header.get("title")?.first().toString()
            val slug: String = it.header.get("slug")?.first().toString()
            val date: String = it.header.get("date")?.first().toString()
            val tags: List<String> = it.header.get("tags")?.toMutableList() ?: listOf("unknown")

            val aricle = Article(
                title,
                slug,
                date,
                tags,
                it.content
            )

            aricle
        }

        println("Success index ${this.articles.size} articles");
    }
}