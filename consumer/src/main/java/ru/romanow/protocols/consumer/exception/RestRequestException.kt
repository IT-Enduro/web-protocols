package ru.romanow.protocols.consumer.exception


class RestRequestException(url: String, status: Int, description: String) :
    RuntimeException("Exception during request to '$url' with status `$status` and description $description")