package com.example.tarush;

sealed class ScreenNavigation(val Route: String) {

    sealed class Screen(val route: String) {
        object Login : Screen("login")
        object Home : Screen("home")
        object ProductDetail : Screen("productDetail/{productId}") {
            // Create function to generate the route with actual product ID
            fun createRoute(productId: String): String {
                return "productDetail/$productId"
            }
        }
        object Checkout : Screen("checkout/{productId}") {
            fun createRoute(productId: String): String {
                return "checkout/$productId"
            }
        }
        object Cart : Screen("cart")
        object Notification : Screen("notification")
        object Profile : Screen("profile")
        object Success : Screen("success")
        object Failed : Screen("failed")
    }
}