package org.example.fake_store_app.shared.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.fake_store_app.primaryColor
import org.example.fake_store_app.shared.screens.auth.LoginScreen
import prefs.Prefs

object SettingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Button(
                    onClick = {
                        navigator.push(ViewOrderHistoryScreen)
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(
                        text = "View Order History", fontSize = 20.sp, modifier = Modifier.padding(8.dp)
                    )
                }
                Box(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        Prefs.putBoolean("islogin", false)
                        navigator.replace(LoginScreen)

                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(
                        text = "LogOut", fontSize = 20.sp, modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}