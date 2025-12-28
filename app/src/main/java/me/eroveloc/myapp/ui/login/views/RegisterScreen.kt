package me.eroveloc.myapp.ui.login.views

import android.Manifest
import android.os.Build
import android.widget.ProgressBar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import me.eroveloc.myapp.ui.login.viewmodels.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen (
    viewModel: RegisterViewModel = koinViewModel(),
    onRegisterSuccess: () -> Unit // Go to Home
) {
    // "Observamos" el estado. Si uiState cambia, esta variable se actualiza y la pantalla se redibuja.
    val state by viewModel.uiState.collectAsState()

    // Manejamos el evento de login exitoso
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registrate",
            style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = state.emailInput,
            onValueChange = { text -> viewModel.onEmailInput(text) },
            label = { Text("Email") },
            placeholder = { Text("usuario@correo.com") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = state.nameInput,
            onValueChange = { text -> viewModel.onNameInput(text) },
            label = { Text("Nombre") },
            placeholder = { Text("Eric") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(
            value = state.passInput,
            onValueChange = { text -> viewModel.onPassInput(text) },
            label = { Text("Contraseña") },
            placeholder = { Text("") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = state.passConfirmInput,
            onValueChange = { text -> viewModel.onPassConfirmInput(text) },
            label = { Text("Confirmar Contraseña") },
            placeholder = { Text("") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        if(state.errorMessage.isNotEmpty()) {
            Text(
                text = state.errorMessage,
                color = Color.Red
            )
        }

        if(state.isBusy) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.onRegisterPress() }
            ) {
                Text("Registrarse")
            }
        }
    }
}