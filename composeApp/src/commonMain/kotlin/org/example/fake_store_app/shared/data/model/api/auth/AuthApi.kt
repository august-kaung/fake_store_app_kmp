package org.example.fake_store_app.shared.data.model.api.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.fake_store_app.shared.api_service.BaseApi
import org.example.fake_store_app.shared.data.model.LoginRequestModel
import org.example.fake_store_app.shared.data.model.LoginResponseModel

interface AuthApi {
    suspend fun login(username: String, password: String): LoginResponseModel
}

class AuthApiImpl(client: HttpClient) : BaseApi(client), AuthApi {
    override suspend fun login(username: String, password: String): LoginResponseModel {
        return postt<LoginResponseModel>("auth/login", LoginRequestModel(username, password))
    }
}
