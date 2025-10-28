package org.example.fake_store_app.shared.data.model.api.auth.home

import org.example.fake_store_app.shared.api_service.BaseApi
import org.example.fake_store_app.shared.data.model.LoginRequestModel
import org.example.fake_store_app.shared.data.model.LoginResponseModel
import org.example.fake_store_app.shared.data.model.MainCategoryModel
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.utils.HttpClientProvider.client

interface HomeApi {
    suspend fun getAllProducts(): List<ProductModel>
    suspend fun getCategories(): List<MainCategoryModel>
    suspend fun searchProduct(searchData: String): List<ProductModel>
    suspend fun getProductByProductId(pid: Int): ProductModel
    suspend fun getProductByCategoryId()

}

class HomeApiImpl : BaseApi(client), HomeApi {
    override suspend fun getAllProducts(): List<ProductModel> {
        return gett<List<ProductModel>>("products", isLoginType = false)
    }

    override suspend fun getCategories(): List<MainCategoryModel> {
        return gett<List<MainCategoryModel>>("categories", isLoginType = false)
    }

    override suspend fun searchProduct(searchData: String): List<ProductModel> {

        return gett<List<ProductModel>>("products/?title=$searchData", isLoginType = false)

    }


    override suspend fun getProductByProductId(pid: Int): ProductModel {
        return gett<ProductModel>("products/$pid", isLoginType = false)

    }

    override suspend fun getProductByCategoryId() {
        TODO("Not yet implemented")
    }

}