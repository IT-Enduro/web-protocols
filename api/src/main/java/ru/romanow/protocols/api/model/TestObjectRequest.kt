package ru.romanow.protocols.api.model

import java.io.Serializable

data class TestObjectRequest(
    var id: Int? = null,
    var searchString: String? = null
) : Serializable