package me.eroveloc.myapp.ui.login.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.eroveloc.myapp.ui.login.viewmodels.SplashViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen (
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit, // Go to Register
    onNavigateToHome: () -> Unit // Go to Home
) {

    // "Observamos" el estado. Si uiState cambia, esta variable se actualiza y la pantalla se redibuja.
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedIn) {
        when (uiState.isLoggedIn) {
            true -> onNavigateToHome()
            false -> onNavigateToLogin()
            null -> { /* Aún cargando o estado inicial */ }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onValidateUserSession()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Loading...")
    }
}