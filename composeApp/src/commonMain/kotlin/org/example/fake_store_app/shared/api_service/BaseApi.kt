package org.example.fake_store_app.shared.api_service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

abstract class BaseApi(val client: HttpClient) {
    var baseUrl: String = "https://fakestoreapi.com/"

    suspend inline fun <reified T> gett(
        endpoint: String, isLoginType: Boolean = true
    ): T {
        baseUrl = if (isLoginType) "https://fakestoreapi.com/" else "https://api.escuelajs.co/api/v1/"
        val response = client.get(baseUrl + endpoint) {
            contentType(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            val errorText = response.bodyAsText()
            throw ApiException("HTTP ${response.status.value}: $errorText")
        }
        return response.body()
    }

    suspend inline fun <reified T> postt(
        endpoint: String, body: Any, isLoginType: Boolean = true
    ): T {
        baseUrl = if (isLoginType) "https://fakestoreapi.com/" else "https://api.escuelajs.co/api/v1/"
        val response = client.post(baseUrl + endpoint) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        if (!response.status.isSuccess()) {
            val errorText = response.bodyAsText()
            throw ApiException("HTTP ${response.status.value}: $errorText")
        }

        return response.body()
    }
}

class ApiException(message: String) : Exception(message)
