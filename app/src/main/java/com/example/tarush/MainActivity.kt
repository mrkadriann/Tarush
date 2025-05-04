package com.example.tarush

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import com.example.tarush.ui.theme.TarushTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tarush.screen.CartScreen
import com.example.tarush.screen.CheckOutScreen
import com.example.tarush.screen.HomeScreen
import com.example.tarush.screen.LoginScreen
import com.example.tarush.screen.NotificationScreen
import com.example.tarush.screen.Product
import com.example.tarush.screen.ProductDetailScreen
import com.example.tarush.ui.theme.TarushTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TarushApp() {
    // Create NavController
    val navController = rememberNavController()

    val productList = remember {
        listOf(
            Product("1", "WATSONS Cetirizine 10mg Tablet (Sold per tablet)", 16, R.drawable.product_cetrizine, "Biñan City, Laguna", 10000, "WATSONS OFFICIAL STORE", R.drawable.icon_watsons, "WATSONS OFFICIAL STORE", R.drawable.icon_watsons, "These tablets effectively alleviate allergy symptoms. They provide relief from minor allergies and are effective within a short period. This product has an extended shelf life."),
            Product("2", "Lucky Me! Pancit Canton", 109, R.drawable.product_pancit_kanton, "Quezon City", 9800, "", null, "LUCKY ME STORE", null, "Popular instant noodles with a rich flavor. Ready in minutes and perfect for a quick meal."),
            Product("3", "Mini Floral Dress", 149, R.drawable.product_mini_floraldress, "Manila", 10000, "", null, "FASHION OUTLET", null, "Beautiful floral pattern mini dress. Comfortable to wear and suitable for casual occasions."),
            Product("4", "Adidas Running Galaxy 6 Shoes Blue", 2400, R.drawable.product_shoes, "Makati", 10000, "", null, "ADIDAS STORE", null, "Premium running shoes with excellent cushioning and support. Designed for comfort during long runs.")
        )
    }

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
        composable(ScreenNavigation.Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(ScreenNavigation.Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = ScreenNavigation.Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Extract the product ID from the route
            val productId = backStackEntry.arguments?.getString("productId")
            // Find the product with matching ID
            val product = productList.find { it.id == productId }

            // If product exists, display its details
            product?.let {
                ProductDetailScreen(product = it, navController = navController )
            }
        }

        composable(
            route = ScreenNavigation.Screen.Checkout.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Extract the product ID from the route
            val productId = backStackEntry.arguments?.getString("productId")
            // Find the product with matching ID
            val product = productList.find { it.id == productId }

            product?.let {
                CheckOutScreen(navController = navController, product = it)
            }
        }

        composable(
            ScreenNavigation.Screen.Cart.route
        ){
            CartScreen(navController)
        }

        composable(
            ScreenNavigation.Screen.Notification.route
        ) {
            NotificationScreen(navController)
        }
    }
}