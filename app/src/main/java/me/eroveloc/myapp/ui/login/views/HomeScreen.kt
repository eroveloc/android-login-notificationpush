package me.eroveloc.myapp.ui.login.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.eroveloc.myapp.ui.login.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen (
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit, // Go to Register
) {

    // "Observamos" el estado. Si uiState cambia, esta variable se actualiza y la pantalla se redibuja.
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoginOut) {
        if(uiState.isLoginOut) {
            onNavigateToLogin()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onRefreshScreen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(uiState.isBusy) {
            CircularProgressIndicator()
        } else {
            if(uiState.isLoaded) {
                Text("Bienvenido ${uiState.name}")
                Button(onClick ={viewModel.onLogoutPress()}) {
                    Text("Cerrar Sesión")
                }
            }
            else
                Text("Ocurrio un error al obtener datos de usuario")
        }
    }
}