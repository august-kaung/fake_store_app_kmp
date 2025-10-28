package org.example.fake_store_app.shared.presentation.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.fake_store_app.shared.data.model.MainCategoryModel
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.data.model.api.auth.home.HomeApiImpl
import org.example.fake_store_app.shared.data.model.repository.DetailsRepository
import org.example.fake_store_app.shared.data.model.repository.DetailsRepositoryImpl
import org.example.fake_store_app.shared.data.model.repository.HomeRepository
import org.example.fake_store_app.shared.data.model.repository.HomeRepositoryImpl

class DetailViewModel(private val repository: DetailsRepository = DetailsRepositoryImpl(HomeApiImpl())) {
    private val _productDetails = MutableStateFlow<ProductModel?>(null)
    private val _isLoading = MutableStateFlow<Boolean>(true)
    val productDetails: StateFlow<ProductModel?> = _productDetails
    val isLoading: StateFlow<Boolean> = _isLoading
    suspend fun loadProductDetails(pid: Int) {
        _isLoading.value = true
        try {
            val result = repository.getProductDetails(pid)
            _productDetails.value = result
        } catch (e: Exception) {
            println("Products error: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }
}