package org.example.fake_store_app.shared.repositories

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.fake_store_app.AppDatabase
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fakestoreapp.Order_items



class OrderRepository(private val db: AppDatabase) {
    fun saveOrder(product: ProductModel , order_id : Int) {


        db.orderItemsQueries.insertOrder(

            title = product.title,
            price = product.price.toDouble(),
            qty = product.qty.toLong(),
            order_id = order_id.toString(),

        )
    }
   fun getNextSlipNo(): Int {

            val maxStr = db.orderItemsQueries.getMaxSlipNo().executeAsOneOrNull()?.MAX
            val max = maxStr?.toIntOrNull() ?: 0
            return max + 1
        }



    fun getOrders(): List<Order_items> {
        return db.orderItemsQueries.selectAll().executeAsList()
    }
}

