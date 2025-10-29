package org.example.fake_store_app.shared.presentation.auth

import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.repositories.OrderRepository
import org.example.fakestoreapp.Order_items
import org.example.fake_store_app.AppDatabase
import org.example.fake_store_app.database
import org.example.fake_store_app.shared.local_db_helper.DatabaseHelper

class CartViewModel {
    private val _cartList = MutableStateFlow<List<ProductModel>>(emptyList())
    val cartList = _cartList.asStateFlow()



    fun addToCart(product: ProductModel , qty : Int =1) {
        val currentList = _cartList.value
        val existing = currentList.find { it.id == product.id }

        if (existing != null) {
            val updated = existing.copy(qty = existing.qty + qty)
            _cartList.value = currentList.map {
                if (it.id == product.id) updated else it
            }
        } else {
            _cartList.value = currentList + product.copy(qty = qty)
        }
    }
    fun clearCart() {
        _cartList.value = emptyList()
    }

    fun removeFromCart(product: ProductModel) {
        _cartList.value = _cartList.value.filterNot { it.id == product.id }
    }

    fun isAlreadyInCart(product: ProductModel): Boolean {
        return _cartList.value.any { it.id == product.id }
    }
    fun updateCount(product: ProductModel, qty: Int) {
        val currentList = _cartList.value
        val existing = currentList.find { it.id == product.id }

        if (existing != null) {
            val updated = existing.copy(qty = qty)
            _cartList.value = currentList.map {
                if (it.id == product.id) updated else it
            }
        }
    }
}