package org.example.fake_store_app.shared.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.fillfav
import fake_store_app.composeapp.generated.resources.minus
import fake_store_app.composeapp.generated.resources.plus
import org.example.fake_store_app.primaryColor
import org.example.fake_store_app.shared.components.ViewModelStore
import org.example.fake_store_app.shared.data.model.ProductModel
import org.jetbrains.compose.resources.painterResource

import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import org.example.fake_store_app.database
import org.example.fakestoreapp.Order_items


object CartScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val repo = org.example.fake_store_app.shared.repositories.OrderRepository(
            database!!
        )

        fun getOrderHistory(): List<Order_items> = repo.getOrders()
        fun confirmCheckout(cartItems: List<ProductModel>) {
            var result = repo.getNextSlipNo()
            var tempOrderId = result
            cartItems.forEach { product ->
                repo.saveOrder(product, tempOrderId)
            }
        }


        val cartViewModel = org.example.fake_store_app.shared.components.ViewModelStore.cartViewModel
        val cartItems = cartViewModel.cartList.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            if (cartItems.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text("No item in cart yet", modifier = Modifier.align(Alignment.Center))
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {

                    LazyColumn(modifier = Modifier.padding(10.dp).weight(1f)) {
                        items(cartItems.value.size) { index ->
                            val product = cartItems.value[index]
                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = { value ->
                                    if (value == SwipeToDismissBoxValue.EndToStart) {
                                        cartViewModel.removeFromCart(product)
                                        true
                                    } else false
                                })

                            SwipeToDismissBox(
                                modifier = Modifier

                                    .padding(vertical = 4.dp),
                                state = dismissState,
                                enableDismissFromEndToStart = true,
                                enableDismissFromStartToEnd = false,
                                backgroundContent = {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Red),
                                        contentAlignment = Alignment.CenterEnd

                                    ) {
                                        Text("Delete    ", color = Color.White)
                                    }
                                },
                                content = {
                                    CartCard(product)
                                })
                        }

                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Total: $${cartItems.value.sumOf { it.price * it.qty }}     ",
                            fontSize = 18.sp,

                            )
                    }


                    Button(
                        onClick = {
                            confirmCheckout(cartItems.value)
                            cartViewModel.clearCart()
                            var a = getOrderHistory()
                            println("Order history size: ${a.toString()}")
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        Text(
                            text = "Confirm Payment", fontSize = 20.sp, modifier = Modifier.padding(8.dp)
                        )
                    }


                }
            }

        }
    }

    @OptIn(InternalVoyagerApi::class)
    @Composable
    fun CartCard(product: ProductModel) {
        val navigator = LocalNavigator.currentOrThrow
        var count by remember { mutableStateOf(product.qty) }

        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(0.dp),
            onClick = {

            }) {
            Row(modifier = Modifier.fillMaxSize()) {
                ProductImage(
                    url = product.images[0], modifier = Modifier.width(100.dp).height(100.dp), isCart = true
                )
                Column(
                    modifier = Modifier.padding(10.dp).fillMaxSize(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "${product.title} @ ${product.price}",
                        fontSize = 16.sp,
                        maxLines = 2, color = primaryColor,

                        )
                    Row(
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "$${product.price * count}",
                            fontSize = 14.sp, color = primaryColor,
                        )
                        IconButton(onClick = {
                            if (count > 0) {
                                count--
                                ViewModelStore.cartViewModel.updateCount(product, count)
                            }

                        }) {
                            Icon(
                                tint = primaryColor,
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(Res.drawable.minus),
                                contentDescription = "Favorite Icon"
                            )
                        }
                        Text(
                            text = count.toString(), fontSize = 20.sp
                        )

                        IconButton(onClick = {
                            count++
                            ViewModelStore.cartViewModel.updateCount(product, count)
                        }) {
                            Icon(
                                tint = primaryColor,

                                modifier = Modifier.size(30.dp),
                                painter = painterResource(Res.drawable.plus),
                                contentDescription = "Cart Item Icon"
                            )
                        }
                    }
                }
            }
        }
    }
}