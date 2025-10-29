package org.example.fake_store_app.shared.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.arrow
import fake_store_app.composeapp.generated.resources.fillfav
import org.example.fake_store_app.primaryColor
import org.example.fake_store_app.shared.components.ViewModelStore
import org.example.fake_store_app.shared.components.ViewModelStore.orderViewModel
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.screens.home.HomeScreen.BottomNavItem
import org.example.fakestoreapp.Order_items
import org.jetbrains.compose.resources.painterResource
import kotlin.collections.emptyList

object ViewOrderHistoryScreen : Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val orderData by ViewModelStore.orderViewModel.orderHistory.collectAsState()
        LaunchedEffect(Unit) {
            ViewModelStore.orderViewModel.loadOrderHistory()
        }

        Scaffold(

            topBar = {
                TopAppBar(
                    title = { Text(text = "Order History") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    actions = {

                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(Res.drawable.arrow),
                                contentDescription = "App Icon"
                            )
                        }
                    },
                )
            }) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            ) {
                LazyColumn(
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(orderData.size) { index ->
                        val singleOrder = orderData[index]
                        OrderCard(singleOrder)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: List<Order_items>) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp), elevation = CardDefaults.cardElevation(4.dp),

        onClick = { /* handle click if needed */ }) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)) {
            Text(
                text = "Order No: ${order.firstOrNull()?.order_id ?: "-"}", fontSize = 20.sp, style = TextStyle(
                    color = primaryColor
                )
            )
            Spacer(Modifier.height(10.dp))

            order.forEach { singleOrder ->
                Box(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text("${singleOrder.title} x${singleOrder.qty} - ${singleOrder.price}")
                }
            }
            val total = order.sumOf { it.qty.toDouble() * it.price }

            Spacer(Modifier.height(10.dp))
            Text(
                text = "Total: $total",
                fontSize = 18.sp,
                style = TextStyle(color = Color.Black)
            )
        }
    }
}
