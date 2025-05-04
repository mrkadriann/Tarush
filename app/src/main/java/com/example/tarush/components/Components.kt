package com.example.tarush.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tarush.R
import com.example.tarush.screen.IDeliveryOption
import com.example.tarush.ui.theme.interFamily
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeliveryDatePicker(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Title
                    Text(
                        text = "Schedule Delivery",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Calendar Header
                    CalendarHeader()

                    // Calendar Grid
                    CalendarGrid(onDateSelected = { dateString ->
                        // After date is selected, show time selection
                        onDateSelected(dateString)
                    })

                    // Cancel button
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 8.dp)
                    ) {
                        Text("Cancel", color = Color(0xFF0D47A1))
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader() {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.from(currentDate) }
    val formatter = remember { DateTimeFormatter.ofPattern("MMMM yyyy") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous month button
        IconButton(onClick = { /* Navigate to previous month */ }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Previous Month",
                tint = Color.Gray
            )
        }

        // Current month and year
        Text(
            text = currentMonth.format(formatter),
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )

        // Next month button
        IconButton(onClick = { /* Navigate to next month */ }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_back), // You'll need to rotate this or use a forward icon
                contentDescription = "Next Month",
                tint = Color.Gray
            )
        }
    }

    // Weekday headers
    Row(modifier = Modifier.fillMaxWidth()) {
        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(onDateSelected: (String) -> Unit) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.from(currentDate) }
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }

    // Calculate first day of month and days in month
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()

    // Calculate padding for first day of month
    val dayOfWeekValue = firstDayOfMonth.dayOfWeek.value % 7 // Adjust to make Sunday 0

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        // Create calendar grid with 6 rows (max needed for any month)
        for (week in 0 until 6) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (day in 0 until 7) {
                    val dayNumber = day + (week * 7) + 1 - dayOfWeekValue

                    if (dayNumber in 1..daysInMonth) {
                        val date = currentMonth.atDay(dayNumber)
                        val isCurrentDate = date == currentDate
                        val isSelected = date == selectedDate.value
                        val isSelectable = date >= currentDate // Only future dates selectable

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isSelected -> Color(0xFF0D47A1)
                                        isCurrentDate -> Color(0xFFE1F5FE)
                                        else -> Color.Transparent
                                    }
                                )
                                .then(
                                    if (isSelectable) {
                                        Modifier.clickable {
                                            selectedDate.value = date
                                            val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
                                            onDateSelected(date.format(formatter))
                                        }
                                    } else {
                                        Modifier
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayNumber.toString(),
                                color = when {
                                    isSelected -> Color.White
                                    !isSelectable -> Color.LightGray
                                    else -> Color.Black
                                },
                                fontWeight = if (isCurrentDate || isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    } else {
                        // Empty box for padding days
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimeSelectionDialog(
    showDialog: Boolean,
    selectedDate: String,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Select Delivery Time",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Selected Date: $selectedDate",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Time slot options
                    val timeSlots = listOf(
                        "Morning (8AM-12PM)",
                        "Afternoon (1PM-5PM)",
                        "Evening (6PM-9PM)"
                    )

                    var selectedTimeSlot by remember { mutableStateOf("") }

                    timeSlots.forEach { timeSlot ->
                        val isSelected = timeSlot == selectedTimeSlot

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isSelected) Color(0xFF0D47A1) else Color.LightGray
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedTimeSlot = timeSlot }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedTimeSlot = timeSlot },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF0D47A1)
                                )
                            )

                            Text(
                                text = timeSlot,
                                modifier = Modifier.padding(start = 8.dp),
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                            )
                        }
                    }

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel", color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (selectedTimeSlot.isNotEmpty()) {
                                    onTimeSelected("$selectedDate - $selectedTimeSlot")
                                    onDismiss()
                                }
                            },
                            enabled = selectedTimeSlot.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0D47A1)
                            )
                        ) {
                            Text("Confirm", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// Usage example in the checkout screen:
// Modify the Schedule Delivery option:
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleDeliveryOption(
    option: IDeliveryOption,
    isSelected: Boolean,
    scheduledDateTime: String,
    onClick: () -> Unit,
    onDateTimeSelected: (String) -> Unit
) {
    val showDatePicker = remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val showTimePicker = remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (isSelected) Color(0xFFE5E5E5) else Color.White),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
                    text = "₱${String.format("%.2f", option.price)}",
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
            onDateTimeSelected(dateTime)
            showTimePicker.value = false
        }
    )
}