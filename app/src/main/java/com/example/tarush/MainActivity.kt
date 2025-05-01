package com.example.tarush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tarush.ui.theme.TarushTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tarush.screen.HomeScreen
import com.example.tarush.screen.LoginScreen
import com.example.tarush.ui.theme.TarushTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarushTheme {
                TarushApp()
            }
        }
    }
}

@Composable
fun TarushApp() {
    // Create NavController
    val navController = rememberNavController()

    // Scaffold provides slot-based layout structure
        // NavHost contains all destinations in the navigation graph
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Screen.Login.route, // Define the starting screen
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        // Define routes and corresponding composables
        composable(ScreenNavigation.Screen.Login.route) { LoginScreen(navController) }
        composable(ScreenNavigation.Screen.Home.route) { HomeScreen(navController) }
    }
}