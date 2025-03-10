package pro.ivanov.services

import pro.ivanov.common.ContentIndexer
import pro.ivanov.models.Article

class ArticleService {
    private val articles = ContentIndexer.instance.articles;

    fun getArticles(): List<Article> {
        return articles.sortedBy { it.date }.reversed()
    }

    fun getLastArticle(): Article {
        return articles.sortedBy { it.date }.reversed().first()
    }

    fun getArticle(slug: String): Article {
        return articles.find { it.slug == slug }!!
    }

    fun getTags(): Set<String> {
        val tags = articles.flatMap { it.tags }.flatMap {it.split(",")}.map { it.trim() }.toSet()

        return tags
    }

    fun getArticlesByTag(tag: String): List<Article> {
       return articles.filter { it.tags.contains(tag) }
    }
}