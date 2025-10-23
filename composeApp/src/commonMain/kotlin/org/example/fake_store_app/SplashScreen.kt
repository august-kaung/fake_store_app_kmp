import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.appicon
import org.example.fake_store_app.auth.LoginScreen

object SplashScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = cafe.adriel.voyager.navigator.LocalNavigator.currentOrThrow
        var showSplash by remember { mutableStateOf(true) }

        if (showSplash) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center
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
            navigator.replace(LoginScreen) // ✅ Replace splash with login
        }
    }
}
