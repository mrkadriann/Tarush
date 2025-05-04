package com.example.tarush.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tarush.R


@Composable
private fun String.ifNotEmpty(block: @Composable (String) -> Unit) {
    if (this.isNotEmpty()) block(this)
}

@Composable
fun CartScreen(
    navController: NavController,
    products: List<Product> = emptyList()
){

    // State for selected products
    val selectedProducts = remember { mutableStateMapOf<String, Boolean>() }
    // State for product quantities
    val productQuantities = remember { mutableStateMapOf<String, Int>() }
    val allSelected = remember { mutableStateOf(false) }

    val productList = if (products.isEmpty()) {
        remember {
            listOf(
                Product("1", "WATSONS Cetirizine 10mg Tablet (Sold per tablet)", 16, R.drawable.product_cetrizine, "Biñan City, Laguna", 10000, "WATSONS OFFICIAL STORE", R.drawable.icon_watsons, "WATSONS OFFICIAL STORE", R.drawable.icon_watsons, "These tablets effectively alleviate allergy symptoms."),
                Product("2", "Lucky Me! Pancit Canton", 109, R.drawable.product_pancit_kanton, "Quezon City", 9800, "", null, "LUCKY ME STORE", null, "Popular instant noodles with a rich flavor."),
                Product("3", "Mini Floral Dress", 149, R.drawable.product_mini_floraldress, "Manila", 10000, "", null, "FASHION OUTLET", null, "Beautiful floral pattern mini dress."),
                Product("4", "Adidas Running Galaxy 6 Shoes Blue", 2400, R.drawable.product_shoes, "Makati", 10000, "", null, "ADIDAS STORE", null, "Premium running shoes with excellent cushioning.")
            )
        }
    } else {
        products
    }

    productList.forEach { product ->
        if (!productQuantities.containsKey(product.id)) {
            productQuantities[product.id] = 1
        }
    }

    // Calculate total based on selected products and their quantities
    val subtotal = productList.sumOf { product ->
        if (selectedProducts[product.id] == true) {
            product.price * (productQuantities[product.id] ?: 1)
        } else {
            0
        }
    }.toDouble()

    // Count selected items
    val selectedItemCount = selectedProducts.count { it.value }

    val areAllSelected = productList.isNotEmpty() &&
            productList.all { selectedProducts[it.id] == true }

    // Only update allSelected if it's different to avoid recomposition loops
    if (allSelected.value != areAllSelected) {
        allSelected.value = areAllSelected
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            VoucherSelectionBar(
                subtotal = subtotal,
                selectedItemCount = selectedItemCount,
                allSelected = allSelected.value,
                onSelectAllChange = { selected ->
                    // Update all products' selection state
                    productList.forEach { product ->
                        selectedProducts[product.id] = selected
                    }
                    allSelected.value = selected
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray) // Default background color
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HeaderGradient {
                    // We add padding at the top for the status bar height
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxSize()
                            .padding(
                                top = WindowInsets.statusBars.asPaddingValues()
                                    .calculateTopPadding(),
                                bottom = 16.dp
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp,)
                                .padding(top = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_left_circle),
                                contentDescription = "Back",
                                colorFilter = ColorFilter.tint(Color.Black),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable{ navController.popBackStack() },
                            )

                            Text(
                                text = "Shopping Cart (4)",
                                color = Color.Black,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 16.dp)
                            )

                            Text(
                                text = "Edit",
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clickable { }
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Gray,
                    thickness = 12.dp
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = paddingValues.calculateBottomPadding())
                        .background(color = Color.White)

                ) {
                    items(productList) { product ->
                        CartProductCard(
                            product = product,
                            quantity = productQuantities[product.id] ?: 1,
                            isSelected = selectedProducts[product.id] ?: false,
                            onSelectionChange = { isSelected ->
                                selectedProducts[product.id] = isSelected
                            },
                            onQuantityChange = { newQuantity ->
                                productQuantities[product.id] = newQuantity
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CartProductCard(
    product: Product,
    quantity: Int = 1,
    isSelected: Boolean = false,
    onSelectionChange: (Boolean) -> Unit = {},
    onQuantityChange: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onSelectionChange(!isSelected) }
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
                spotColor = Color.Black
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Store selection checkbox and name
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onSelectionChange(it) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Gray,
                        uncheckedColor = Color.Gray
                    )
                )

                if (product.shopLogoResId != null) {
                    product.hiddenshopName.ifNotEmpty {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = product.shopLogoResId),
                                contentDescription = "Shop logo",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Go to store",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                } else {
                    product.hiddenshopName.ifNotEmpty {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.icon_store),
                                contentDescription = "Shop logo",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Go to store",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Product details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .background(color = Color.White)
            ) {
                // Left checkbox for product selection
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { onSelectionChange(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Gray,
                            uncheckedColor = Color.Gray
                        )
                    )
                }

                // Product image
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.White, RoundedCornerShape(4.dp)),
                ) {
                    product.imageResId?.let {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                // Product information
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Price and quantity controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "₱${product.price}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // Quantity selector
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            // Decrease button
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                    .clickable {
                                        if (quantity > 1) onQuantityChange(quantity - 1)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "−",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }

                            // Quantity text
                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .widthIn(min = 24.dp)
                                    .border(BorderStroke(1.dp, Color.LightGray))
                                    .padding(horizontal = 12.dp),
                                textAlign = TextAlign.Center,
                            )

                            // Increase button
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                    .clickable {
                                        onQuantityChange(quantity + 1)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Voucher banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_discount),
                        contentDescription = "Voucher",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Up to ₱40 off voucher available",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.icon_arrow_right),
                    contentDescription = "View voucher",
                    colorFilter = ColorFilter.tint(Color.Gray),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun VoucherSelectionBar(
    subtotal: Double,
    selectedItemCount: Int,
    allSelected: Boolean,
    onSelectAllChange: (Boolean) -> Unit
) {
    BottomAppBar(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Gray, RoundedCornerShape(4.dp))
            .height(130.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_discount),
                        contentDescription = "Vouchers",
                        modifier = Modifier.size(34.dp)
                    )

                    Text(
                        text = "Vouchers",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select or enter code →",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        onCheckedChange = { onSelectAllChange(it) },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = Color.Gray,
                            checkedColor = Color.Gray
                        ),
                        modifier = Modifier.padding(end = 2.dp),
                        checked = allSelected
                    )

                    Text(
                        text = "All",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "₱${String.format("%.2f", subtotal)}",
                        fontWeight = FontWeight.Medium
                    )

                    Button(
                        onClick = { /* Handle checkout */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9C7CF4)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Check Out (${selectedItemCount})",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderGradient(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
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
