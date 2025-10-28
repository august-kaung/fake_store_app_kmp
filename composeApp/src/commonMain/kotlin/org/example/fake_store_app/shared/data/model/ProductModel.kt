package org.example.fake_store_app.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductModel(
    val category: Category,
    val creationAt: String,
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val slug: String,
    val title: String,
    val updatedAt: String,
    var qty: Int = 1,
)