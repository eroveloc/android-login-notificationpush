package me.eroveloc.myapp.ui.login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.eroveloc.myapp.data.services.IUserService
import me.eroveloc.myapp.data.services.requests.UserRegisterRequest
import me.eroveloc.myapp.domain.models.User
import me.eroveloc.myapp.domain.repositories.IUserRepository

class RegisterViewModel (
    private val userService: IUserService,
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onRegisterPress() {

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBusy = true, errorMessage = "") }

                var email = _uiState.value.emailInput
                var name = _uiState.value.nameInput
                var pass = _uiState.value.passInput
                var passConfirm = _uiState.value.passConfirmInput

                if(email.isEmpty() || name.isEmpty() || pass.isEmpty()) {
                    _uiState.update {
                        it.copy(errorMessage = "Por favor, completa todos los campos")
                    }
                    return@launch
                }

                if(pass.length < 6) {
                    _uiState.update {
                        it.copy(errorMessage = "La contraseña debe tener al menos 8 caracteres")
                    }
                    return@launch
                }

                if (pass != passConfirm) {
                    _uiState.update {
                        it.copy(errorMessage = "Las contraseñas no coinciden")
                    }
                    return@launch
                }

                var response = userService.userRegister(UserRegisterRequest(email, name, pass))

                if (response != null && response.success == true) {
                    _uiState.update { it.copy(isLoggedIn = true) }
                    userRepository.addUser(
                        User(
                            id = response.id?: 0,
                            email = response.email?: "no-email",
                            name = response.name?: "no-name"
                        )
                    )
                }


            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.message
                            ?: "Error conectando con el servidor, intenta de nuevo"
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(isBusy = false)
                }
            }
        }
    }

    fun onEmailInput(email: String) {
        _uiState.update {
            it.copy(emailInput = email)
        }
    }

    fun onNameInput(name: String) {
        _uiState.update {
            it.copy(nameInput = name)
        }
    }

    fun onPassInput(pass: String) {
        _uiState.update {
            it.copy(passInput = pass)
        }
    }

    fun onPassConfirmInput(passConfirm: String) {
        _uiState.update {
            it.copy(passConfirmInput = passConfirm)
        }
    }
}

data class RegisterUiState(
    val emailInput: String = "",
    val nameInput: String = "",
    val passInput: String = "",
    val passConfirmInput: String = "",
    val errorMessage: String = "",
    val isLoggedIn: Boolean = false,
    val isBusy: Boolean = false,
)