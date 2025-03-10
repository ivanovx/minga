package pro.ivanov.common

class Constants {
    companion object {
        val contentRoot = System.getenv("CONTENT_ROOT").orEmpty()
    }
}