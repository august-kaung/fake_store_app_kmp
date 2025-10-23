package org.example.fake_store_app

import SplashScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import org.example.fake_store_app.auth.LoginScreen

@Composable
fun BorderedTextField(hintText: String, keyboardType: KeyboardType = KeyboardType.Text) {
    var text by remember { mutableStateOf("") }

}

@Composable

fun App() {

    Navigator(SplashScreen)

}
