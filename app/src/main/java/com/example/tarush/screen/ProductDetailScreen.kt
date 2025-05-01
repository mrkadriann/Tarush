package com.example.tarush.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tarush.R

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
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomActionBar()
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
    onBackClick: () -> Unit
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
                    text = "${product.hiddenshopName.uppercase()} OFFICIAL STORE",
                    color = Color(0xFF00A9B7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Right side - Cart icon and more menu
            Row {
                IconButton(onClick = { /* Handle cart navigation */ }) {
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
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = product.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 16.dp)
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
                    fontSize = 14.sp
                )
                Text(
                    text = "₱0 - ₱55",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "More Info",
                tint = Color.Gray
            )
        }

        Divider()

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
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "More Info",
                tint = Color.Gray
            )
        }

        Divider()

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
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "More Info",
                tint = Color.Gray
            )
        }

        Divider()

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
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating Star",
                tint = Color(0xFFFFB700),
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Product Ratings (1.5k)",
                fontSize = 14.sp
            )
        }

        // Review summary
        Text(
            text = "Review Summary",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Text(
            text = product.reviewSummary,
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        // Sold count
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "${product.soldCount}+ sold",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { /* Handle favorite */ }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Add to Favorites",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun BottomActionBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Add to Cart button
            Button(
                onClick = { /* Handle add to cart */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Add to Cart"
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Add to Cart",
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Buy Now button
            Button(
                onClick = { /* Handle buy now */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB6BD9),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(
                    text = "Buy Now",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Chat with seller button
            IconButton(
                onClick = { /* Handle chat with seller */ },
                modifier = Modifier.size(48.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_chat),
                        contentDescription = "Chat with Seller",
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray),
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = "Chat",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    // Sample product for preview
    val sampleProduct = Product(
        id = "1",
        name = "WATSONS Cetirizine 10mg Tablet (Sold per tablet)",
        price = 16,
        imageResId = null, // Replace with actual resource ID
        location = "Manila",
        soldCount = 10000,
        shopName = "Watsons",
        shopLogoResId = null, // Replace with actual resource ID
        hiddenshopName = "Watsons",
        hiddenshopLogoResId = null, // Replace with actual resource ID
        reviewSummary = "These tablets effectively alleviate allergy symptoms. They provide relief from minor allergies and are effective within a short period. This product has an extended shelf life."
    )
}