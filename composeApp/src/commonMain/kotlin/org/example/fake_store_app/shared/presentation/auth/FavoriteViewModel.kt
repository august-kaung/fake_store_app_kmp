package org.example.fake_store_app.shared.presentation.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.fake_store_app.shared.data.model.ProductModel

class FavoriteViewModel {
    private val _favList = MutableStateFlow<List<ProductModel>>(mutableListOf())
    val favList = _favList.asStateFlow()

    fun addToFavorite(product: ProductModel) {
        if (_favList.value.any { it.id == product.id }) return
        _favList.value = _favList.value + product
    }

    fun removeFromFavorite(product: ProductModel) {
        _favList.value = _favList.value.filterNot { it.id == product.id }
    }

    fun isAlreadyFavorite(product: ProductModel) =
        _favList.value.any { it.id == product.id }
}