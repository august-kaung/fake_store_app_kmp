package org.example.fake_store_app.shared.presentation.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.fake_store_app.shared.data.model.MainCategoryModel
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.data.model.api.auth.home.HomeApiImpl
import org.example.fake_store_app.shared.data.model.repository.HomeRepository
import org.example.fake_store_app.shared.data.model.repository.HomeRepositoryImpl
import org.example.fake_store_app.shared.utils.HttpClientProvider

class HomeViewModel(private val repository: HomeRepository = HomeRepositoryImpl(HomeApiImpl())) {
    private val _products = MutableStateFlow<List<ProductModel>>(mutableListOf())
    private val _categories = MutableStateFlow<List<MainCategoryModel>>(mutableListOf())
    val products: StateFlow<List<ProductModel>> = _products
    val categories: StateFlow<List<MainCategoryModel>> = _categories

    suspend fun loadProducts() {
        try {
            val result = repository.getAllProducts()
            _products.value = result
        } catch (e: Exception) {
            println("Products error: ${e.message}")
        }
    }

    suspend fun loadCategories() {
        try {
            val result = repository.getAllCategories()
            _categories.value = result
        } catch (e: Exception) {
            println("Category error: ${e.message}")

        }
    }
}