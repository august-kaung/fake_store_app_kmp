package org.example.fake_store_app.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MainCategoryModel(
    val id: Int,
    val image: String,
    val name: String,
    val slug: String
)