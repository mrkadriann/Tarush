package com.example.tarush.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tarush.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationScreen(
    navController: NavController = rememberNavController()
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Column {
                // Top navigation bar
                TopBar(navController)

                // Tab Row
                OrderTabs()
            }
        },
        content = { paddingValues ->
            // Content with padding from scaffold
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Empty order list content
                EmptyOrderContent()
            }
        },
        containerColor = Color.White
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(onClick = { navController.popBackStack() }) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
            )
        }

        // Title
        Text(
            text = "My Orders",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        // Search field (simplified)
        CustomSearchBar()

        // Chat button
        IconButton(onClick = { /* Handle chat */ }) {
            Image(
                painter = painterResource(id = R.drawable.icon_chat),
                contentDescription = "Chat",
                modifier = Modifier
                    .size(24.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
    }
}

@Composable
fun OrderTabs() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("All", "To pay", "To ship", "To receive", "Complete")

    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 16.dp,
            containerColor = Color.White,
            contentColor = Color(0xFFFF4081),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(3.dp),
                    color = Color(0xFFFF4081)
                )
            },
            divider = { Divider(color = Color.LightGray, thickness = 1.dp) }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color(0xFFFF4081) else Color.Gray,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // Optional: Content or view under tabs depending on selection
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun EmptyOrderContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Empty cart illustration
        EmptyCartIllustration()

        Spacer(modifier = Modifier.height(16.dp))

        // Empty message
        Text(
            text = "Your order list is empty",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Shop now button
        ShopNowButton()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomSearchBar() {
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(30.dp)
            .shadow(2.dp, RoundedCornerShape(20.dp), clip = false)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.3f))
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}


@Composable
fun EmptyCartIllustration() {
    // This is a simplified representation of the cart illustration
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tarush_logo),
            contentDescription = "Empty Cart",
            modifier = Modifier.size(250.dp)
        )
    }
}

@Composable
fun ShopNowButton() {
    Button(
        onClick = { /* Handle shop now action */ },
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        // Gradient background for button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF5252),  // Red-Pink
                            Color(0xFF7C4DFF)   // Purple
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Shop now",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
