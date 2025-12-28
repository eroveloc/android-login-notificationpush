package me.eroveloc.myapp.ui.login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.eroveloc.myapp.data.services.IUserService
import me.eroveloc.myapp.data.services.requests.UserLoginRequest
import me.eroveloc.myapp.domain.models.User
import me.eroveloc.myapp.domain.repositories.IUserRepository

class LoginViewModel (
    private val userService: IUserService,
    private val userRepository: IUserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginPress() {

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBusy = true) }
                _uiState.update { it.copy(errorMessage = "") }

                var email = _uiState.value.emailInput
                var pass = _uiState.value.passInput

                if(email.isEmpty() || email.isEmpty()) {
                    _uiState.update {
                        it.copy(errorMessage = "Por favor, completa todos los campos")
                    }
                    return@launch
                }

                var response = userService.userLogin(UserLoginRequest(email,pass))

                if(response.success == true){
                    _uiState.update { it.copy(isLoggedIn = true) }
                    userRepository.addUser(User(
                        id = response.id?: 0,
                        email = response.email?: "no-email",
                        name = response.name?: "no-name"
                    ))
                }
                else
                    _uiState.update { it.copy(errorMessage = response.message?: "Error conectando con el servidor, intenta de nuevo") }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message ?: "Error conectando con el servidor, intenta de nuevo")
                }
            } finally {
                _uiState.update { it.copy(isBusy = false) }
            }
        }
    }

    fun onEmailInput(email: String) {
        _uiState.update {
            it.copy(emailInput = email)
        }
    }

    fun onPassInput(pass: String) {
        _uiState.update {
            it.copy(passInput = pass)
        }
    }
}

data class LoginUiState(
    val emailInput: String = "",
    val passInput: String = "",
    val errorMessage: String = "",
    val isLoggedIn: Boolean = false,
    val isBusy: Boolean = false,
)