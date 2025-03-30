package ru.romanow.protocols.api.model

enum class Purpose {
    FRONTEND,
    BACKEND,
    DATABASE
}

fun findPurpose(purpose: String): Purpose {
    return Purpose.entries.first { it.name == purpose }
}
