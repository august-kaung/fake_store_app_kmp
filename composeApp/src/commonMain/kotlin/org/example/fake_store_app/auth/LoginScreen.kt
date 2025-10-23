package org.example.fake_store_app.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.shopfont
import org.example.fake_store_app.BorderedTextField
import org.example.fake_store_app.home.HomeScreen
import org.example.fake_store_app.primaryColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font


object LoginScreen : Screen {

    @Composable
    override fun Content() {
        var emailText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }
        var emailErrorText by remember { mutableStateOf("") }
        var passwordErrorText by remember { mutableStateOf("") }
        val navigator = cafe.adriel.voyager.navigator.LocalNavigator.currentOrThrow


        @OptIn(ExperimentalResourceApi::class) val poppinsFont = FontFamily(
            Font(Res.font.shopfont, weight = FontWeight.Medium)
        )


        Box(
            modifier = Modifier.fillMaxSize().background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),

                    text = "Fancy Store", textAlign = TextAlign.Center, style = TextStyle(
                        fontSize = 36.sp, fontFamily = poppinsFont, color = primaryColor
                    )
                )
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(

                    value = emailText, onValueChange = {
                        emailText = it
                        emailErrorText = ""
                    },

                    textStyle = TextStyle(fontSize = 20.sp), keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ), singleLine = true, placeholder = {
                        Text(
                            text = "Email Address", style = TextStyle(fontSize = 20.sp, color = primaryColor)
                        )
                    }, modifier = Modifier.padding(8.dp).fillMaxWidth(), shape = RoundedCornerShape(12.dp)
                )
                if (emailErrorText.isNotEmpty()) {
                    Text(
                        textAlign = TextAlign.Left,
                        text = emailErrorText,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                    )
                }
                OutlinedTextField(

                    value = passwordText,

                    onValueChange = {
                        passwordText = it
                        passwordErrorText = ""
                    }, textStyle = TextStyle(fontSize = 20.sp), keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ), singleLine = true, placeholder = {
                        Text(
                            text = "Password", style = TextStyle(fontSize = 20.sp, color = primaryColor)
                        )
                    }, modifier = Modifier.padding(8.dp).fillMaxWidth(), shape = RoundedCornerShape(12.dp)
                )
                if (passwordErrorText.isNotEmpty()) {
                    Text(
                        textAlign = TextAlign.Left,
                        text = passwordErrorText,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                    )
                }
                Button(

                    onClick = {
                        when {
                            emailText.isEmpty() -> emailErrorText = " Email cannot be empty"
                            passwordText.isEmpty() -> passwordErrorText = " Password cannot be empty"
                            else -> navigator.push(item = HomeScreen)
                        }
                    },
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    )

                ) {
                    Text(
                        text = "Login", style = TextStyle(
                            fontSize = 20.sp,

                            ), modifier = Modifier.padding(8.dp)
                    )
                }


            }
        }
    }
}

