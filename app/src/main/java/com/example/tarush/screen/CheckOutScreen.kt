package com.example.tarush.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import com.example.tarush.R
import com.example.tarush.components.DeliveryDatePicker
import com.example.tarush.components.TimeSelectionDialog
import com.example.tarush.ui.theme.interFamily

data class IProduct(
    val id: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    // We'll add imageResourceId later and replace with actual image resources
)

data class IDeliveryOption(
    val id: String,
    val name: String,
    val price: Double,
    val description: String?,
    val icon: Int, // Resource ID for the icon
    val isEditable: Boolean = false // Flag to determine if this option can be edited (for scheduling)
)

data class IPaymentMethod(
    val id: String,
    val name: String,
    val lastFourDigits: String? = null,
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(navController: NavController, product: Product) {
    // Sample data
    var scheduledDateTime by remember { mutableStateOf("Select a date and time.") }

    val deliveryOptions = listOf(
        IDeliveryOption(
            id = "1",
            name = "Fast",
            price = 5.99,
            description = "Guaranteed to get today (March 24).",
            icon = R.drawable.icon_delifast // Replace with your actual icon resource
        ),
        IDeliveryOption(
            id = "2",
            name = "Standard",
            price = 39.00,
            description = "Guaranteed to get by March 25-27.",
            icon = R.drawable.icon_delistandard // Replace with your actual icon resource
        ),
        IDeliveryOption(
            id = "3",
            name = "Schedule Delivery",
            price = 19.99,
            description = scheduledDateTime, // This will update when the user selects a date/time
            icon = R.drawable.icon_delicustom, // Replace with your actual icon resource
            isEditable = true
        )
    )


    val paymentMethods = listOf(
        IPaymentMethod("1", "Cash on Delivery"),
        IPaymentMethod("2", "Gcash"),
    )

    // Create a product item using the actual product data with a state-backed quantity
    var productQuantity by remember { mutableStateOf(1) } // Default to 1

    // Create a product item using the actual product data
    val productItem = IProduct(
        id = product.id,
        name = product.name,
        price = product.price.toDouble(), // Convert from Int to Double
        quantity = productQuantity
    )

    val products = listOf(productItem)

    // State for selected options
    var selectedDeliveryOption by remember { mutableStateOf(deliveryOptions[0].id) }
    var selectedPaymentMethod by remember { mutableStateOf(paymentMethods[0].id) }

    // Calculate totals
    val subtotal = products.sumOf { it.price * it.quantity }
    val deliveryFee = deliveryOptions.find { it.id == selectedDeliveryOption }?.price ?: 0.0
    val total = subtotal + 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Checkout",
                        fontSize = 16.sp,
                    ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(R.drawable.icon_back),
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(205, 205, 205, 100),
                    thickness = 6.dp
                )
            }
            // Shipping Address Section
            item {
                DeliveryAddressCard()
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(205, 205, 205, 100),
                    thickness = 6.dp
                )
            }

            // Order Summary Section
            item {
                Text(
                    text = product.hiddenshopName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(start = 16.dp),
                    color = Color.Black
                )
            }

            item {
                ProductCard(
                    product = product,
                    quantity = productQuantity,
                    onQuantityChanged = { newQuantity ->
                        productQuantity = newQuantity
                    },
                    subtotal = subtotal
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(205, 205, 205, 100),
                    thickness = 6.dp
                )
            }

            //Delivery Options Section
            item {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_deliveryv2),
                        contentDescription = "Delivery",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Delivery Options",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontFamily = interFamily
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Standard and Fast delivery options
                    deliveryOptions.filter { it.id != "3" }.forEach { option ->
                        val isSelected = option.id == selectedDeliveryOption
                        val borderColor = if (isSelected) Color(0xFFE5E5E5) else Color.White
                        val backgroundColor = if (isSelected) Color.White else Color.White

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, borderColor),
                            colors = CardDefaults.cardColors(containerColor = backgroundColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedDeliveryOption = option.id }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = option.name,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontFamily = interFamily
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 2.dp),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = option.icon),
                                            contentDescription = option.name,
                                            modifier = Modifier.size(24.dp)
                                        )

                                        Text(
                                            text = option.description ?: "",
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = interFamily
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "₱${if (option.price == 39.0) "39" else String.format("%.2f", option.price)}",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontFamily = interFamily
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .border(
                                                width = 1.dp,
                                                color = if (isSelected) Color.Transparent else Color(0xFF0D47A1),
                                                shape = CircleShape
                                            )
                                            .background(
                                                color = if (isSelected) Color.Transparent else Color.Transparent,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Image(
                                                painter = painterResource(id = R.drawable.icon_selected),
                                                contentDescription = "Selected",
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Schedule Delivery Option with Calendar
                    val scheduleOption = deliveryOptions.find { it.id == "3" }
                    if (scheduleOption != null) {
                        val isSelected = scheduleOption.id == selectedDeliveryOption
                        val showDatePicker = remember { mutableStateOf(false) }
                        var selectedDate by remember { mutableStateOf("") }
                        val showTimePicker = remember { mutableStateOf(false) }

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isSelected) Color(0xFFE5E5E5) else Color.White),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedDeliveryOption = scheduleOption.id }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = scheduleOption.name,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontFamily = interFamily
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 2.dp),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = scheduleOption.icon),
                                            contentDescription = scheduleOption.name,
                                            modifier = Modifier.size(24.dp)
                                        )

                                        Text(
                                            text = scheduledDateTime,
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = interFamily
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { showDatePicker.value = true },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.icon_edit),
                                            contentDescription = "Edit date and time",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "₱${String.format("%.2f", scheduleOption.price)}",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontFamily = interFamily
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .border(
                                                width = 1.dp,
                                                color = if (isSelected) Color.Transparent else Color(0xFF0D47A1),
                                                shape = CircleShape
                                            )
                                            .background(
                                                color = if (isSelected) Color.Transparent else Color.Transparent,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Image(
                                                painter = painterResource(id = R.drawable.icon_selected),
                                                contentDescription = "Selected",
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Date picker dialog
                        DeliveryDatePicker(
                            showDialog = showDatePicker.value,
                            onDismiss = { showDatePicker.value = false },
                            onDateSelected = { date ->
                                selectedDate = date
                                showDatePicker.value = false
                                showTimePicker.value = true
                            }
                        )

                        // Time picker dialog
                        TimeSelectionDialog(
                            showDialog = showTimePicker.value,
                            selectedDate = selectedDate,
                            onDismiss = { showTimePicker.value = false },
                            onTimeSelected = { dateTime ->
                                scheduledDateTime = dateTime
                                selectedDeliveryOption = scheduleOption.id // Auto-select this option
                                showTimePicker.value = false
                            }
                        )
                    }
                }
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(205, 205, 205, 100),
                    thickness = 6.dp
                )
            }

            // Payment Method Section
            item {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_payment),
                        contentDescription = "Payment",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Payment Method",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 8.dp),
                        color = Color.Black,
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    paymentMethods.forEach { method ->
                        val isSelected = method.id == selectedPaymentMethod
                        val borderColor = if (isSelected) Color.LightGray else Color.White
                        val backgroundColor = if (isSelected) Color.White else Color.White

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = isSelected,
                                    onClick = { selectedPaymentMethod = method.id }
                                )
                                .padding(horizontal = 16.dp)
                                .border(
                                    BorderStroke(1.dp, borderColor),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            colors = CardDefaults.cardColors(containerColor = backgroundColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = method.name,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                    if (method.lastFourDigits != null) {
                                        Text(
                                            text = "**** **** **** ${method.lastFourDigits}",
                                            color = Color.Gray
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .border(
                                            width = 1.dp,
                                            color = if (isSelected) Color.Transparent else Color(0xFF0D47A1),
                                            shape = CircleShape
                                        )
                                        .background(
                                            color = if (isSelected) Color.Transparent else Color.Transparent,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Image(
                                            painter = painterResource(id = R.drawable.icon_selected),
                                            contentDescription = "Selected",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(205, 205, 205, 100),
                    thickness = 6.dp
                )
            }

            item {
                OrderSummary(
                    subtotal = subtotal,
                    deliveryFee = deliveryFee,
                    total = total
                )
            }
        }
    }
}

// Don't forget to define Icons.Filled.CheckCircle if needed
@Composable
fun Icons.Filled.CheckCircle(contentDescription: String, tint: Color, modifier: Modifier) {
    // This is a placeholder for the actual icon
    // In a real implementation, you would use the actual icon from the library
    Box(
        modifier = modifier
            .background(tint, RoundedCornerShape(50))
    ) {
        Text(
            text = "✓",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 16.sp
        )
    }
}

@Composable
fun DeliveryAddressCard(
    name: String = "Juan Dela Cruz",
    phone: String = "(63+) 992 354 7654",
    address: String = "112 7th Avenue, Caloocan City, Metro Manila",
    onEditClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_location), // Use your own location icon
                    contentDescription = "Location",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Delivery Address",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            IconButton(onClick = onEditClick) {
                Image(
                    painter = painterResource(id = R.drawable.icon_edit), // Use your own edit icon
                    contentDescription = "Edit",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Row() {
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .width(28.dp)
            )
            Column() {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = phone,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = address,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    quantity: Int,
    onQuantityChanged: (Int) -> Unit,
    subtotal: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Product image
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                ) {
                    if (product.imageResId != null) {
                        Image(
                            painter = painterResource(id = product.imageResId),
                            contentDescription = product.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Quantity selector (dummy, non-functional)
                        Row(
                            modifier = Modifier
                                .border(1.dp, Color.Gray, RoundedCornerShape(50))
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Minus button
                            Box(
                                modifier = Modifier
                                    .clickable(enabled = quantity > 1) {
                                        if (quantity > 1) onQuantityChanged(quantity - 1)
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "–",
                                    fontWeight = FontWeight.Bold,
                                    color = if (quantity > 1) Color.Black else Color.Gray
                                )
                            }

                            // Quantity display
                            Text(
                                text = quantity.toString(),
                                modifier = Modifier.padding(horizontal = 8.dp),
                                fontWeight = FontWeight.Medium
                            )

                            // Plus button
                            Box(
                                modifier = Modifier
                                    .clickable { onQuantityChanged(quantity + 1) }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(100.dp))

                        // The price will be the unit price * quantity
                        Text(
                            text = "₱${String.format("%.2f", subtotal)}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1), // Dark blue
                            fontSize = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderSummary(
    subtotal: Double,
    deliveryFee: Double,
    total: Double
) {
    // Calculate saved amount (assuming it's the difference between subtotal + deliveryFee and total)
    val saved = subtotal + deliveryFee - total
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Order Summary",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Merchandise Total",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Handle voucher application */ },
                    modifier = Modifier
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.LightGray),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Vouchers",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Free Shipping",
                            fontSize = 12.sp,
                            color = Color.Green,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Total",
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                            Text(
                                text = "₱${String.format("%.2f", total)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF7E41DA),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Text(
                            text = "Saved ₱${String.format("%.2f", deliveryFee)}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Button(
                        onClick = { /* Handle order placement */ },
                        modifier = Modifier.height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7E41DA)
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                    ) {
                        Text(
                            text = "Order Now",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
