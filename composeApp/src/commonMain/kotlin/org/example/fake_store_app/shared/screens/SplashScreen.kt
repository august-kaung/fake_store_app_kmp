package org.example.fake_store_app.shared.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.appicon
import kotlinx.coroutines.delay
import org.example.fake_store_app.shared.screens.auth.LoginScreen
import org.example.fake_store_app.shared.screens.home.HomeScreen
import org.jetbrains.compose.resources.painterResource
import prefs.Prefs

object SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var showSplash by remember { mutableStateOf(true) }

        if (showSplash) {
            Box(
                modifier = Modifier.Companion.fillMaxSize().background(Color.Companion.White),
                contentAlignment = Alignment.Companion.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.appicon), contentDescription = "Fancy Online Shop"
                )
            }

            LaunchedEffect(Unit) {
                delay(3000)
                showSplash = false
            }
        } else {
            var res = Prefs.getBoolean("islogin", false)
            if(res){
                navigator.replace(HomeScreen)
            }else{
                navigator.replace(LoginScreen)

            }
        }
    }
}