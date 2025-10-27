package org.example.fake_store_app.shared.presentation.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.fake_store_app.shared.data.model.repository.AuthRepository


sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val token: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel(private val repository: AuthRepository) {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = _state

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading
            try {
                val response = repository.login(username, password)
                println("res : $response")
                _state.value = AuthUiState.Success(response.token)
            } catch (e: Exception) {
                println("Login error: ${e.message}")
                e.printStackTrace()
                _state.value = AuthUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
