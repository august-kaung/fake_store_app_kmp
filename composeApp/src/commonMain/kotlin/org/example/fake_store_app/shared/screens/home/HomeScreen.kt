package org.example.fake_store_app.shared.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.base
import fake_store_app.composeapp.generated.resources.cart
import fake_store_app.composeapp.generated.resources.favorite
import fake_store_app.composeapp.generated.resources.filter
import fake_store_app.composeapp.generated.resources.home
import fake_store_app.composeapp.generated.resources.info
import fake_store_app.composeapp.generated.resources.search
import fake_store_app.composeapp.generated.resources.setting
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.example.fake_store_app.primaryColor
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.presentation.auth.HomeViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


object HomeScreen : Screen {
    sealed class BottomNavItem(val route: String, val label: String, val icon: DrawableResource) {
        object Home : BottomNavItem("home", "Home", Res.drawable.base)
        object Favorite : BottomNavItem("favorite", "Favorite", Res.drawable.favorite)
        object Cart : BottomNavItem("cart", "Cart", Res.drawable.cart)
        object Settings : BottomNavItem("settings", "Settings", Res.drawable.setting)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var selectedTab by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
        var appBarText by remember { mutableStateOf<String>("Fancy Online Shop") }

        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = appBarText) }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ), actions = {

                })
        }, bottomBar = {
            NavigationBar {
                listOf(
                    BottomNavItem.Home, BottomNavItem.Favorite, BottomNavItem.Cart, BottomNavItem.Settings
                ).forEach { item ->
                    NavigationBarItem(selected = selectedTab == item, onClick = {
                        when (item) {
                            is BottomNavItem.Home -> appBarText = "Fancy Online Shop"
                            is BottomNavItem.Favorite -> appBarText = "Favorite"
                            is BottomNavItem.Cart -> appBarText = "Cart"
                            is BottomNavItem.Settings -> appBarText = "Setting"
                        }
                        selectedTab = item

                    }, icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(item.icon),
                            contentDescription = item.label
                        )
                    }, label = { Text(item.label) })
                }
            }
        }) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center
            ) {
                when (selectedTab) {
                    is BottomNavItem.Home -> RealHome()
                    is BottomNavItem.Favorite -> Text("Favorites Screen")
                    is BottomNavItem.Cart -> Text("Cart Screen")
                    is BottomNavItem.Settings -> Text("Settings Screen")
                }
            }
        }
    }

}

@Composable
fun ProductGrid(products: List<ProductModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 👈 2 items per row
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product)
        }
    }
}

@Composable
fun ProductCard(product: ProductModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            KamelImage(
                resource = asyncPainterResource(product.images.first()),
                contentDescription = product.title,
                onLoading = { Text("Loading...") },
                onFailure = { Text("Failed to load") },
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = product.title,
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                fontSize = 16.sp
            )
        }
    }
}



@Composable
fun RealHome(viewModel: HomeViewModel = remember { HomeViewModel() }) {
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
        viewModel.loadCategories()
    }
    var searchText by remember { mutableStateOf("") }
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()


    Box(
        modifier = Modifier.fillMaxSize().background(Color.Red), contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(

                    value = searchText, onValueChange = {
                        searchText = it

                    },

                    textStyle = TextStyle(fontSize = 20.sp), keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ), singleLine = true, placeholder = {
                        Text(
                            text = "Search item", style = TextStyle(fontSize = 20.sp, color = primaryColor)
                        )
                    }, modifier = Modifier.padding(8.dp), shape = RoundedCornerShape(12.dp)
                )
                IconButton(

                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.search),
                        contentDescription = "Info Icon",
                        tint = primaryColor,
                        modifier = Modifier.size(25.dp)
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.filter),
                        contentDescription = "Info Icon",
                        tint = primaryColor,
                        modifier = Modifier.size(25.dp)
                    )
                }

            }
            if (products.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Loading products...")
                }
            } else {
                ProductGrid(products)
            }
        }
    }

}