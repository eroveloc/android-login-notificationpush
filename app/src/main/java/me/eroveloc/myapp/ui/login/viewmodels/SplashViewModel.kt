package me.eroveloc.myapp.ui.login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.eroveloc.myapp.domain.repositories.IUserRepository

class SplashViewModel (
    private val userRepository: IUserRepository,
) : ViewModel() {

    // State
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState : StateFlow<SplashUiState> = _uiState.asStateFlow()

    // ValidateUser
    fun onValidateUserSession() {

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBusy = true) }

                val response = userRepository.getUser()

                if(response != null && response.email.isNotEmpty())
                    _uiState.update { it.copy(isLoggedIn = true) }
                else
                    _uiState.update { it.copy(isLoggedIn = false) }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message ?: "Error")
                }
            } finally {
                _uiState.update { it.copy(isBusy = false) }
            }
        }
    }
}

data class SplashUiState(
    val isLoggedIn: Boolean? = null,
    val isBusy: Boolean = false,
    val errorMessage: String? = null
)