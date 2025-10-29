package org.example.fake_store_app.shared.components

import org.example.fake_store_app.shared.presentation.auth.CartViewModel
import org.example.fake_store_app.shared.presentation.auth.DetailViewModel
import org.example.fake_store_app.shared.presentation.auth.FavoriteViewModel
import org.example.fake_store_app.shared.presentation.auth.HomeViewModel
import org.example.fake_store_app.shared.presentation.auth.OrderHistoryViewModel
import org.example.fake_store_app.shared.screens.home.DetailScreenView

object ViewModelStore {
    val favoriteViewModel = FavoriteViewModel()
    val homeViewModel = HomeViewModel()
    val detailsViewModel = DetailViewModel()
    val cartViewModel = CartViewModel()

    val orderViewModel = OrderHistoryViewModel()

}