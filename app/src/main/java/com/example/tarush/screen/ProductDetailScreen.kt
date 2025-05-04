package com.example.tarush.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tarush.R
import com.example.tarush.ScreenNavigation

@Composable
fun ProductDetailScreen(
    product: Product,
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(bottom = 20.dp),
        topBar = {
            TopAppBar(
                product = product,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            BottomActionBar(
                navController = navController,
                product = product,
                onBuyClick = { navController.navigate(ScreenNavigation.Screen.Checkout.createRoute(product.id)) })
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Product Image Section
            ProductImageSection(product = product)

            // Product Details Section
            ProductInfoSection(product = product)

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    product: Product,
    onBackClick: () -> Unit,
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side - Back button
            IconButton(onClick =  onBackClick ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Gray
                )
            }

            // Center - Store name
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Replace this with actual store logo
                product.shopLogoResId?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = "Store Logo",
                        modifier = Modifier.size(32.dp)
                    )
                } ?: run {
                    // Placeholder for shop logo when not available
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFF00A9B7), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = product.hiddenshopName.first().toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${product.hiddenshopName.uppercase()}",
                    color = Color(0xFF00A9B7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Right side - Cart icon and more menu
            Row {
                IconButton(onClick = { navController.navigate(ScreenNavigation.Screen.Cart.route) }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.Gray
                    )
                }

                IconButton(onClick = { /* Handle more options */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ProductImageSection(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.White)
    ) {
        // Product image
        product.imageResId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = product.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        } ?: run {
            // Placeholder for when image is not available
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Product Image",
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ProductInfoSection(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Price with currency and product name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "₱${product.price}",
                color = Color.Blue,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.icon_share),
                contentDescription = "Share",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "${product.soldCount}+ sold",
                color = Color.Black,
                fontSize = 14.sp
            )

            IconButton(onClick = { /* Handle favorite */ }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Add to Favorites",
                    tint = Color.Gray
                )
            }
        }

        Text(
            text = product.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color.Black
        )

        Divider(
            color = Color.Black,
            thickness = 1.dp
        )

        // Delivery info section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_delivery),
                contentDescription = "Location",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Guaranteed to get today (Mar 24), with shipping fee",
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    text = "₱0 - ₱55",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "More Info",
                tint = Color.Gray
            )
        }

        Divider(
            color = Color.Black,
            thickness = 1.dp
        )

        // Authentic guarantee
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_verified),
                contentDescription = "Verified",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "100% Authentic  •  Free & Easy Returns",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "More Info",
                tint = Color.Gray
            )
        }

        Divider(
            color = Color.Black,
            thickness = 1.dp
        )

        // Cashback info
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_coins),
                contentDescription = "Cashback",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "₱50 cashback if your order arrives late.",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "More Info",
                tint = Color.Gray
            )
        }

        Divider(
            color = Color.Black,
            thickness = 1.dp
        )

        // Product ratings
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "4.8",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating Star",
                tint = Color(0xFFFFB700),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Product Ratings (1.5k)",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Divider(
            color = Color.Black,
            thickness = 1.dp
        )

        // Review summary
        Text(
            text = "Review Summary",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            color = Color.Black
        )

        Text(
            text = product.reviewSummary,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun BottomActionBar(
    navController: NavController,
    product: Product,
    onBuyClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Add to Cart button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_shopping),
                    contentDescription = "Add to Cart",
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray),
                    modifier = Modifier.size(42.dp)
                )
                Text(
                    text = "Add to Cart",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick =  onBuyClick ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBB6BD9),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(horizontal = 16.dp)// Add shadow here
                        .width(200.dp)
                ) {
                    Text(
                        text = "Buy Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_chat),
                    contentDescription = "Chat with Seller",
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray),
                    modifier = Modifier.size(42.dp)
                )

                Text(
                    text = "Chat Seller",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
