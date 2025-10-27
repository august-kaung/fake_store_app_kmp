package org.example.fake_store_app.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponseModel(
    val token: String
)
