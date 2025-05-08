package com.example.tarush.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tarush.R
import com.example.tarush.ScreenNavigation

@Composable
fun EditProfileScreen(
    navController: NavController = rememberNavController()
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Column {
                TopBarEditProfile(navController)
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                EditProfileContent()
            }
        }
    )
}

@Composable
fun TopBarEditProfile(
    navController: NavController
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.icon_epback),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                )
            }

            Text(
                text = "Edit Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { navController.navigate(ScreenNavigation.Screen.Home.route) }) {
                Image(
                    painter = painterResource(id = R.drawable.icon_save),
                    contentDescription = "Save",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Black,
            modifier = Modifier
                .shadow(
                elevation = 12.dp,
                spotColor = Color.Black
            )
        )
    }
}

@Composable
fun EditProfileContent(

) {
    val gradientColors = listOf(
        Color(0xFFFF6D00), // Orange
        Color(0xFFAA00FF)  // Purple
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp)
                .background(Color.Black)
        ) {
            Text(
                text = "Tap To Change",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 8.dp)
        )
        {
            Text(
                text = "Username",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gender",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Set Now",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Text(
                    text = ">",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Birthday",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Set Now",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Text(
                    text = ">",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Phone",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Set Now",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Text(
                    text = ">",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Email",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Set Now",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Text(
                    text = ">",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Social Media Accounts",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Set Now",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Text(
                    text = ">",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Change Password",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Set Now",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )

                Text(
                    text = ">",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}