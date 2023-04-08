package ru.romanow.protocols.common.client.service

interface ServerClient {
    fun create(purpose: String): String
    fun findAll(): String
    fun getById(id: Int): String
    fun findInCity(city: String): String
    fun update(id: Int, purpose: String?, latency: Int?, bandwidth: Int?, city: String?, country: String?): String
}