package org.example.fake_store_app.shared.screens.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.arrow
import fake_store_app.composeapp.generated.resources.cart
import fake_store_app.composeapp.generated.resources.favorite
import fake_store_app.composeapp.generated.resources.fillfav
import fake_store_app.composeapp.generated.resources.info
import fake_store_app.composeapp.generated.resources.minus
import fake_store_app.composeapp.generated.resources.plus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.fake_store_app.primaryColor
import org.example.fake_store_app.shared.components.ViewModelStore
import org.example.fake_store_app.shared.data.model.ProductModel
import org.example.fake_store_app.shared.data.model.api.auth.home.HomeApiImpl
import org.example.fake_store_app.shared.data.model.repository.DetailsRepositoryImpl
import org.example.fake_store_app.shared.presentation.auth.DetailViewModel
import org.example.fake_store_app.shared.presentation.auth.FavoriteViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class DetailScreen(var pid: Int) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    override fun Content() {
        var productDetails by remember { mutableStateOf<ProductModel?>(null) }
        var isLoading by remember { mutableStateOf(true) }


        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(pid) {
            isLoading = true
            try {
                val repo = DetailsRepositoryImpl(HomeApiImpl())
                productDetails = withContext(Dispatchers.Default) {
                    repo.getProductDetails(pid)
                }
            } catch (e: Exception) {
                println("Error loading product: ${e.message}")
            } finally {
                isLoading = false
            }
        }
        Scaffold(

            modifier = Modifier.fillMaxSize(), topBar = {
                TopAppBar(
                    navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(Res.drawable.arrow),
                            contentDescription = "App Icon"
                        )
                    }
                }, title = {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(24.dp).clip(RoundedCornerShape(4.dp))
                    ) {

                        val title = productDetails?.title ?: "Product Details"

                        Text(
                            text = "Product Details", fontSize = 20.sp, modifier = Modifier.fillMaxWidth()
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
                )
            }

        ) { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.TopCenter
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    productDetails != null -> DetailScreenView(productDetails!!)
                    else -> Text("Failed to load product.")
                }
            }

        }
    }
}

@Composable
fun DetailScreenView(product: ProductModel) {


    var count by remember { mutableStateOf<Int>(1) }

    var favoriteViewModel = ViewModelStore.favoriteViewModel
    val favorites by favoriteViewModel.favList.collectAsState()
    var isFav by remember {
        mutableStateOf(favoriteViewModel.isAlreadyFavorite(product))
    }
    println("Favorites: ${favoriteViewModel.favList.value}")
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        ProductImage(
            url = product.images.first(),
            modifier = Modifier.width(300.dp).height(300.dp).padding(16.dp).clip(
                RoundedCornerShape(8.dp)
            ),
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {
                isFav = !isFav
                if (isFav) {
                    favoriteViewModel.addToFavorite(product)
                } else {
                    favoriteViewModel.removeFromFavorite(product)
                }
                println("Favorites: ${favoriteViewModel.favList.value}")
            }) {
                if (isFav == true) Icon(
                    tint = primaryColor,
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(Res.drawable.fillfav),
                    contentDescription = "Favorite Icon"
                ) else Icon(
                    tint = primaryColor,
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(Res.drawable.favorite),
                    contentDescription = "Favorite Icon"
                )
            }
            IconButton(onClick = {
                if (count > 0) {
                    count--
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
            }) {
                Icon(
                    tint = primaryColor,

                    modifier = Modifier.size(30.dp),
                    painter = painterResource(Res.drawable.plus),
                    contentDescription = "Cart Item Icon"
                )
            }
            IconButton(onClick = {
                ViewModelStore.cartViewModel.addToCart(product, count)
            }) {
                Icon(
                    tint = primaryColor,

                    modifier = Modifier.size(30.dp),
                    painter = painterResource(Res.drawable.cart),
                    contentDescription = "Cart Item Icon"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Title", fontSize = 20.sp, modifier = Modifier.weight(1f)
            )
            Text(
                text = product.title,
                fontSize = 18.sp,
                color = primaryColor,
                softWrap = true,
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Price", fontSize = 20.sp, modifier = Modifier.weight(1f)
            )
            Text(
                text = "$${product.price}", fontSize = 18.sp, color = primaryColor
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Description", fontSize = 20.sp, modifier = Modifier.weight(1f)
            )
            Text(
                text = product.description,
                fontSize = 18.sp,
                color = primaryColor,
                modifier = Modifier.weight(1f),
                style = TextStyle(
                    textAlign = TextAlign.Justify
                ),
                softWrap = true
            )

        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Category", fontSize = 20.sp, modifier = Modifier.weight(1f)
            )
            Text(
                text = product.category.name, fontSize = 18.sp, color = primaryColor
            )
        }


    }
}

@Composable
fun MarqueeText(text: String) {
    var textWidthPx by remember { mutableStateOf(0f) }
    val density = LocalDensity.current
    val transition = rememberInfiniteTransition()

    val offsetX by transition.animateFloat(
        initialValue = 0f, targetValue = -textWidthPx, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing), repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier.fillMaxWidth().height(24.dp), contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.offset(x = offsetX.dp).onGloballyPositioned { layoutCoordinates ->
                textWidthPx = layoutCoordinates.size.width.toFloat()
            })
    }
}

@Composable
fun IconWithBadge(
    count: Int, icon: @Composable () -> Unit
) {
    Box(contentAlignment = Alignment.TopEnd) {
        icon()
        if (count > 0) {
            Box(
                modifier = Modifier.size(16.dp).background(Color.Red, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count.toString(), fontSize = 10.sp, color = Color.White
                )
            }
        }
    }
}


