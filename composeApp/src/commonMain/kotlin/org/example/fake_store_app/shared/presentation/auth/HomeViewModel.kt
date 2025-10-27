package org.example.fake_store_app.shared.presentation.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import org.example.fake_store_app.shared.data.model.MainCategoryModel
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.data.model.api.auth.home.HomeApiImpl
import org.example.fake_store_app.shared.data.model.repository.HomeRepository
import org.example.fake_store_app.shared.data.model.repository.HomeRepositoryImpl
import org.example.fake_store_app.shared.utils.HttpClientProvider

class HomeViewModel(private val repository: HomeRepository = HomeRepositoryImpl(HomeApiImpl())) {
    private val _products = MutableStateFlow<List<ProductModel>>(mutableListOf())
    private val _categories = MutableStateFlow<List<MainCategoryModel>>(mutableListOf())
    private val _isLoading = MutableStateFlow(true)
    val products: StateFlow<List<ProductModel>> = _products
    val categories: StateFlow<List<MainCategoryModel>> = _categories
    val isLoading: StateFlow<Boolean> = _isLoading


    suspend fun loadProducts(isSearch: Boolean = false, query: String = "") {
        _isLoading.value = true
        try {
            if (isSearch) {
                val result = repository.getSearchData(query)
                _products.value = result
            } else {
                val result = repository.getAllProducts()
                _products.value = result
            }

        } catch (e: Exception) {
            println("Products error: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun loadCategories() {
        try {
            val result = repository.getAllCategories()
            _categories.value = mutableListOf<MainCategoryModel>(
                MainCategoryModel(
                    id = 0, name = "All", image = "", slug = "All Categories"
                )
            ) + result
        } catch (e: Exception) {
            println("Category error: ${e.message}")

        }
    }

    suspend fun filterProductsByCategory(categoryName: String) {
        if (categoryName == "All") {
            loadProducts()
        } else {
            if (_products.value.isEmpty()) {
                loadProducts()
            }
            val filteredProducts =
                _products.value.filter { it.category.name.lowercase().equals(categoryName.lowercase()) }
            _products.value = filteredProducts
        }
    }
}