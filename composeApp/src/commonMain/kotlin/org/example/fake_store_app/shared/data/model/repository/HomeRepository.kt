package org.example.fake_store_app.shared.data.model.repository

import io.ktor.client.HttpClient
import org.example.fake_store_app.shared.data.model.MainCategoryModel
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.data.model.api.auth.home.HomeApi

interface HomeRepository {
    suspend fun getAllProducts(): List<ProductModel>
    suspend fun getAllCategories(): List<MainCategoryModel>
}


class HomeRepositoryImpl(private val api: HomeApi) : HomeRepository {
    override suspend fun getAllProducts(): List<ProductModel> {
        return api.getAllProducts()
    }

    override suspend fun getAllCategories(): List<MainCategoryModel> {
        return api.getCategories()
    }

}
