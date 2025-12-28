package me.eroveloc.myapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.eroveloc.myapp.ui.login.views.HomeScreen
import me.eroveloc.myapp.ui.login.views.LoginScreen
import me.eroveloc.myapp.ui.login.views.RegisterScreen
import me.eroveloc.myapp.ui.login.views.SplashScreen

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        // 0. Pantalla de Splash (Placeholder temporal)
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 1. Pantalla de Login
        composable(Screen.Login.route) {
            LoginScreen(
                // Asumiendo que modificaste LoginScreen como vimos en el paso anterior
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    // Navegar a Home y borrar Login de la historia (backstack)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Pantalla de Registro (Placeholder temporal)
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    // Navegar a Home y borrar Register de la historia (backstack)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Pantalla Home (Placeholder temporal)
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}