package org.example.fake_store_app.shared.presentation.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.fake_store_app.database
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.data.model.api.auth.home.HomeApiImpl
import org.example.fake_store_app.shared.data.model.repository.DetailsRepository
import org.example.fake_store_app.shared.data.model.repository.DetailsRepositoryImpl
import org.example.fakestoreapp.Order_items

class OrderHistoryViewModel {
    private val _orderHistory = MutableStateFlow<List<List<Order_items>>>(emptyList())

    val orderHistory: StateFlow<List<List<Order_items>>> = _orderHistory
    val repo = org.example.fake_store_app.shared.repositories.OrderRepository(
        database!!
    )


     fun loadOrderHistory() {
        try {
            val result = repo.getOrders()
            _orderHistory.value = result.groupBy { it.order_id }.values.toList()
        } catch (e: Exception) {
            println("Fetching error: ${e.message}")
        }
    }
}
