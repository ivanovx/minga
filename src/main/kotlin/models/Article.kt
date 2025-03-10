package pro.ivanov.models

import java.time.LocalDate

data class Article(
    val title: String,
    val slug: String,
    val date: LocalDate,
    val tags: List<String>,
    val content: String,
)