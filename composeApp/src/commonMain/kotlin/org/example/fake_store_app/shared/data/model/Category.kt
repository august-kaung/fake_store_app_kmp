package org.example.fake_store_app.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val creationAt: String,
    val id: Int,
    val image: String,
    val name: String,
    val slug: String,
    val updatedAt: String
)