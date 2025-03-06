package pro.ivanov.models

data class Article(
    val title: String,
    val slug: String,
    val date: String,
    val tags: List<String>,
    val content: String,
)
