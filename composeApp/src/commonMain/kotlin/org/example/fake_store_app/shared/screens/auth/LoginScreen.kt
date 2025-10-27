package org.example.fake_store_app.shared.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fake_store_app.composeapp.generated.resources.Res
import fake_store_app.composeapp.generated.resources.info
import fake_store_app.composeapp.generated.resources.shopfont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.fake_store_app.shared.screens.home.HomeScreen
import org.example.fake_store_app.primaryColor
import org.example.fake_store_app.shared.data.model.api.auth.AuthApiImpl
import org.example.fake_store_app.shared.data.model.repository.AuthRepositoryImpl
import org.example.fake_store_app.shared.presentation.auth.AuthUiState
import org.example.fake_store_app.shared.presentation.auth.AuthViewModel
import org.example.fake_store_app.shared.utils.HttpClientProvider
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


object LoginScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)

    @Composable

    override fun Content() {
        var emailText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }
        var emailErrorText by remember { mutableStateOf("") }
        var passwordErrorText by remember { mutableStateOf("") }
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = remember { AuthViewModel(AuthRepositoryImpl(AuthApiImpl(HttpClientProvider.client))) }
        val state by viewModel.state.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()


        @OptIn(ExperimentalResourceApi::class) val poppinsFont = FontFamily(
            Font(Res.font.shopfont, weight = FontWeight.Medium)
        )


        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {}, colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ), actions = {
                        IconButton(onClick = {
                            emailText = "johnd"
                            passwordText = "m38rmF$"
                        }) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(Res.drawable.info),
                                contentDescription = "App Icon"
                            )
                        }
                    })
            },

            ) {
            Box(modifier = Modifier.fillMaxSize()) {
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
                                text = "Username", style = TextStyle(fontSize = 20.sp, color = primaryColor)
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
                                emailText.isEmpty() -> emailErrorText = " Username cannot be empty"
                                passwordText.isEmpty() -> passwordErrorText = " Password cannot be empty"
                                else -> viewModel.login(emailText, passwordText)
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


                    when (state) {
                        is AuthUiState.Loading -> {
                            LoadingDialog()
                        }

                        is AuthUiState.Success -> {

                            LaunchedEffect(Unit) {
                                launch {
                                    snackbarHostState.showSnackbar("Login Successful", duration = SnackbarDuration.Indefinite)
                                }
                                delay(1000)
                                snackbarHostState.currentSnackbarData?.dismiss()
                                navigator.push(HomeScreen)
                            }
                        }

                        is AuthUiState.Error -> {
                            LaunchedEffect((state as AuthUiState.Error).message) {
                                scope.launch {
                                    snackbarHostState.showSnackbar((state as AuthUiState.Error).message)
                                }
                            }
                        }

                        else -> {}
                    }


                }
            }
        }
    }
}

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { /* block dismiss */ }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        // Just the spinner, centered
        CircularProgressIndicator(color = primaryColor)
    }
}



