package org.example.fake_store_app.shared.data.model.repository

import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.data.model.api.auth.home.HomeApi

interface DetailsRepository {
    suspend fun getProductDetails(pid: Int): ProductModel
}

class DetailsRepositoryImpl(private val api: HomeApi) : DetailsRepository {
    override suspend fun getProductDetails(pid: Int): ProductModel {
        return api.getProductByProductId(pid)
    }


}