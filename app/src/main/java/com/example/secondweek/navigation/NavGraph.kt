package com.example.secondweek.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.secondweek.ui.screens.ForgotPasswordScreen
import com.example.secondweek.ui.screens.LoginScreen
import com.example.secondweek.ui.screens.RegisterScreen

object Routes {
    const val Login = "login"
    const val Register = "register"
    const val Forgot = "forgot"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Login) {
        composable(Routes.Login) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Routes.Register) },
                onNavigateToForgot = { navController.navigate(Routes.Forgot) }
            )
        }
        composable(Routes.Register) {
            RegisterScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.Forgot) {
            ForgotPasswordScreen(onBack = { navController.popBackStack() })
        }
    }
}




