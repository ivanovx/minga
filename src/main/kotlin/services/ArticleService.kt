package pro.ivanov.services

import pro.ivanov.common.ContentIndexer
import pro.ivanov.models.Article

class ArticleService {
    private val articles = ContentIndexer.instance.articles;

    fun getArticles(): List<Article> {
        return articles.sortedBy { it.date }.reversed()
    }

    fun getArticle(slug: String): Article {
        return articles.find { it.slug == slug }!!
    }
}