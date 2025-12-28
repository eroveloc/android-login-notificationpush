package me.eroveloc.myapp.ui.login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.eroveloc.myapp.domain.repositories.IUserRepository

class HomeViewModel (
    private val userRepository: IUserRepository,
) : ViewModel() {

    // State
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Refresca pantalla
    fun onRefreshScreen() {

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBusy = true) }

                val response = userRepository.getUser()

                if(response != null && response.email.isNotEmpty())
                    _uiState.update { it.copy(name = response.name, isLoaded = true) }
                else
                    _uiState.update { it.copy(isLoaded = false, errorMessage = "Ocurrio un error al obtener datos de usuario") }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message ?: "Error")
                }
            } finally {
                _uiState.update { it.copy(isBusy = false) }
            }
        }
    }

    fun onLogoutPress() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBusy = true) }
                val result = userRepository.removeUser()

                if(result)
                    _uiState.update { it.copy(isLoginOut = true) }
                else
                    _uiState.update { it.copy(errorMessage = "Ocurrio un error al cerrar sesión") }
            }
            catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message ?: "Error")
                }
            }
        }
    }
}

data class HomeUiState(
    val isLoaded: Boolean = false,
    val isLoginOut: Boolean = false,
    val name: String = "",
    val isBusy: Boolean = false,
    val errorMessage: String? = null
)