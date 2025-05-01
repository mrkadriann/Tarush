package com.example.tarush.screen

import android.R.color
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tarush.R
import com.example.tarush.ScreenNavigation

// Data class for product items
data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val imageResId: Int?,
    val location: String,
    val soldCount: Int,
    val shopName: String,
    val shopLogoResId: Int?,
    val hiddenshopName: String,
    val hiddenshopLogoResId: Int?,
    val reviewSummary: String
)

// Data class for categories
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    products: List<Product> = emptyList()
) {
    // Sample data
    val categories = remember {
        listOf(
            Category("1", "Food & Beverages", "food_image_url"),
            Category("2", "Women's Apparel", "women_apparel_url"),
            Category("3", "Men's Wear", "men_wear_url"),
            Category("4", "Home & Living", "home_living_url"),
            Category("5", "Sports", "sports_url")
        )
    }

    val productList = if (products.isEmpty()) {
        remember {
            listOf(
                Product("1", "WATSONS Cetirizine 10mg Tablet", 16, R.drawable.product_cetrizine, "Biñan City, Laguna", 10000, "WATSONS OFFICIAL STORE", R.drawable.icon_watsons, "WATSONS", R.drawable.icon_watsons, "These tablets effectively alleviate allergy symptoms."),
                Product("2", "Lucky Me! Pancit Canton", 109, R.drawable.product_pancit_kanton, "Quezon City", 9800, "", null, "LUCKY ME", null, "Popular instant noodles with a rich flavor."),
                Product("3", "Mini Floral Dress", 149, R.drawable.product_mini_floraldress, "Manila", 10000, "", null, "FASHION OUTLET", null, "Beautiful floral pattern mini dress."),
                Product("4", "Adidas Running Galaxy 6 Shoes Blue", 2400, R.drawable.product_shoes, "Makati", 10000, "", null, "ADIDAS", null, "Premium running shoes with excellent cushioning.")
            )
        }
    } else {
        products
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set status bar color to transparent
            window.statusBarColor = Color.Transparent.toArgb()
            // This is the key line to make the app content draw behind system bars
            WindowCompat.setDecorFitsSystemWindows(window, false)
            // Set status bar icon color to light (white) to contrast with the purple gradient
            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = false // Use light (white) icons for dark background
            }
        }
    }

    // Use the Scaffold with a transparent containerColor to allow our gradient to show through
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Navigate to Home */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = Color(0xFF673AB7)
                        )
                        Text(
                            text = "Home",
                            fontSize = 12.sp,
                            color = Color(0xFF673AB7)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Navigate to Wallet */ }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_wallet),
                            contentDescription = "Wallet",
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black),
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Wallet",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Navigate to Notifications */ }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.Gray
                        )
                        Text(
                            text = "Notifications",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Navigate to Profile */ }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Me",
                            tint = Color.Gray
                        )
                        Text(
                            text = "Me",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        // Main background gradient that spans the entire screen including status bar
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Full screen gradient background that will show in the status bar area too
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White) // Default background color
            )

            // Main content column
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header with shadow effect - note we're not padding for insets since we want content behind status bar
                HeaderWithShadow {
                    // We add padding at the top for the status bar height
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                                bottom = 16.dp
                            )
                    ) {
                        // Search bar and icons
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp,)
                                .padding(top = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Search field
                            OutlinedTextField(
                                value = "",
                                onValueChange = { },
                                placeholder = {
                                    Text(
                                        "Search",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(start = 4.dp)
                                    ) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = Color.Gray,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                },
                                trailingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.icon_camera),
                                        contentDescription = "Filter",
                                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black),
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clickable { }
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .shadow(
                                        elevation = 50.dp,
                                        shape = RoundedCornerShape(12.dp),
                                        spotColor = Color.Black
                                    ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.LightGray,
                                    focusedBorderColor = Color.LightGray,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                ),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            // Cart icon
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { }
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            // Chat icon
                            Image(
                                painter = painterResource(id = R.drawable.icon_chat),
                                contentDescription = "Chat",
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Categories text
                        Text(
                            text = "CATEGORIES",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Categories row
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)

                        ) {
                            items(categories) { category ->
                                CategoryItem(category = category)
                            }
                        }
                    }
                }

                // Products grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    items(productList) { product ->
                        ProductCard(
                            product = product,
                            onProductClick = {
                                // Navigate to product detail screen with the product ID
                                navController.navigate(ScreenNavigation.Screen.ProductDetail.createRoute(product.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(55.dp)
                .shadow(
                    elevation = 50.dp,
                    shape = CircleShape,
                    spotColor = Color.Black
                )
                .clip(CircleShape)
                .background(Color.White)
                .clickable { },
            contentAlignment = Alignment.Center,
        ) {
            // TODO: Replace with actual category image
            // Example: Image(painter = painterResource(id = R.drawable.category_icon), contentDescription = category.name)

            // This is a placeholder icon
            when(category.name) {
                "Food & Beverages" -> Image(
                    painter = painterResource(id = R.drawable.icon_food_and_beverages),
                    contentDescription = category.name,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF2196F3)),
                )
                "Women's Apparel" -> Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = category.name,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(30.dp)
                )
                "Men's Wear" -> Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = category.name,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(30.dp)
                )
                "Home & Living" -> Image(
                    painter = painterResource(id = R.drawable.icon_furniture),
                    contentDescription = category.name,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF2196F3)),
                )
                else -> Image(
                    painter = painterResource(id = R.drawable.icon_sports),
                    contentDescription = category.name,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF2196F3)),
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category.name,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,  // Increased line height for better spacing between lines
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 2f
                )
            ),
            maxLines = 2,
            overflow = TextOverflow.Clip,
        )
    }
}

@Composable
fun ProductCard(
    product: Product,
    onProductClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        onClick =  onProductClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Only show shop information if shopName is not empty
            if (product.shopName.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .background(Color.White)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 6.dp)
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Display shop logo if available
                        if (product.shopLogoResId != null) {
                            Image(
                                painter = painterResource(id = product.shopLogoResId),
                                contentDescription = "Shop Logo",
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            // Fallback icon if no logo provided
                            Image(
                                painter = painterResource(id = R.drawable.icon_camera),
                                contentDescription = "Shop Logo",
                                modifier = Modifier.size(20.dp),
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF2196F3))
                            )
                        }

                        Text(
                            text = product.shopName,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0, 156, 167, 100)
                        )
                    }
                }
            }

            // Product image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                if (product.imageResId != null) {
                    // Display actual product image from resources
                    Image(
                        painter = painterResource(id = product.imageResId),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    // Fallback when no image is available
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PRODUCT\nIMAGE",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Product info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Price with Philippine peso symbol
                Text(
                    text = "₱${product.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF673AB7)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Location and sold count
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )

                    Text(
                        text = product.location,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${product.soldCount}+ sold",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun HeaderWithShadow(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .shadow(
                elevation = 30.dp,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                spotColor = Color.Black
            ),
    ) {
        // Main gradient background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF673AB7),   // Deep Purple at top
                            Color(0xFF9C27B0),   // Purple in the middle
                            Color(0xFFD81B60),   // Pink
                            Color(0xFFE65100)    // Deep Orange at bottom
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}