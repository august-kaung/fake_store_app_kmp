package org.example.fake_store_app.shared.data.model.repository

import org.example.fake_store_app.shared.data.model.LoginResponseModel
import org.example.fake_store_app.shared.data.model.api.auth.AuthApi

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResponseModel
}

class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {
    override suspend fun login(username: String, password: String): LoginResponseModel {
        return api.login(username, password)
    }
}
