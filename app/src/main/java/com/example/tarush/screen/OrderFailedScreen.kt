package com.example.tarush.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tarush.R
import com.example.tarush.ScreenNavigation

@Composable
fun OrderFailedScreen (
    navController: NavController = rememberNavController()
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Column {
                OrderFailedTopBar(navController)
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                OrderFailedContent(navController)
            }
        }
    )
}

@Composable
fun OrderFailedTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun OrderFailedContent(
    navController: NavController
) {
    val gradientColors = listOf(
        Color(0xFFFF6D00), // Orange
        Color(0xFFAA00FF)  // Purple
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp) // Adjust height as needed
                .background(
                    color = Color.LightGray, // Light gray color
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                ),
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Spacer(
                    modifier = Modifier
                        .height(60.dp)
                )

                Text(
                    text = "Order Failed",
                    fontSize = 22.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    text = "Go to my purchases for more info.",
                    color = Color.White,
                    modifier = Modifier.padding(top = 20.dp)
                )

                Spacer(
                    modifier = Modifier
                        .height(82.dp)
                )

                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(44.dp)
                        .background(Color.White)
                        .clickable { navController.navigate(ScreenNavigation.Screen.Notification.route) },
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "My Purchases",
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun OrderFailedScreenPreview() {
    OrderFailedScreen()
}
