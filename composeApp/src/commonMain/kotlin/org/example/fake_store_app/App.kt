package org.example.fake_store_app

import org.example.fake_store_app.shared.screens.SplashScreen
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.navigator.Navigator


@Composable
fun BorderedTextField(hintText: String, keyboardType: KeyboardType = KeyboardType.Text) {
    var text by remember { mutableStateOf("") }

}
var database : AppDatabase ?= null
@Composable

fun App(db : AppDatabase) {
    database = db
    Navigator(SplashScreen)

}
