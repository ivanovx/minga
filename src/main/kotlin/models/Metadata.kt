package pro.ivanov.models

data class Metadata(
    val title: String,
    val slug: String,
    val date: String,
    val tags: List<String>,
)