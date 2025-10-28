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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.arrow
import fake_store_app.composeapp.generated.resources.favorite
import fake_store_app.composeapp.generated.resources.fillfav
import org.example.fake_store_app.primaryColor
import org.example.fake_store_app.shared.components.ViewModelStore
import org.example.fake_store_app.shared.data.model.ProductModel
import org.jetbrains.compose.resources.painterResource

object FavoriteScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val favoriteViewModel = org.example.fake_store_app.shared.components.ViewModelStore.favoriteViewModel
        val favorites = favoriteViewModel.favList.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            if (favorites.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text("No favorites yet", modifier = Modifier.align(Alignment.Center))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(favorites.value.size) { index ->
                        val product = favorites.value[index]
                        FavCard(product)
                    }
                }
            }
        }

    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
fun FavCard(product: ProductModel) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp).height(100.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            navigator.push(DetailScreen(product.id))
        }) {
        Row(modifier = Modifier.fillMaxSize()) {
            ProductImage(
                url = product.images[0],
                modifier = Modifier.width(100.dp).height(100.dp).clip(RoundedCornerShape(8.dp)),
                isFav = true
            )
            Column(
                modifier = Modifier.padding(10.dp).fillMaxSize(),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
            ) {
                Text(
                    text = product.title,
                    fontSize = 16.sp,
                    maxLines = 2, color = primaryColor,

                    )
                Row(
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "$${product.price}",
                        fontSize = 14.sp, color = primaryColor,
                    )
                    IconButton(onClick = {
                        ViewModelStore.favoriteViewModel.removeFromFavorite(product)
                    }) {
                        Icon(
                            tint = primaryColor,
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(Res.drawable.fillfav),
                            contentDescription = "Favorite Icon"
                        )
                    }
                }
            }
        }
    }
}