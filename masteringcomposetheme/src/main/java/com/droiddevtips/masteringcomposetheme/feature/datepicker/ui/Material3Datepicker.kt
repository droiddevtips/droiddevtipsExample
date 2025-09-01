@file:OptIn(ExperimentalMaterial3Api::class)

package com.droiddevtips.masteringcomposetheme.feature.datepicker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.droiddevtips.masteringcomposetheme.feature.customCard.ui.AppCustomCard

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun Material3DatePicker(modifier: Modifier = Modifier) {

    var showDatePicker by remember { mutableStateOf(false) }
    var showDatePickerRangeSelection by remember { mutableStateOf(false) }
    var showDatePickerWithInput by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    AppCustomCard(modifier = modifier, title = "Date/time picker") {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.padding(all = 16.dp), onClick = {

                showDatePicker = true

            }) {
                Text("Open Date picker")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.padding(all = 16.dp), onClick = {

                showDatePickerWithInput = true

            }) {
                Text("Open Date picker with input")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.padding(all = 16.dp), onClick = {

                showDatePickerRangeSelection = true

            }) {
                Text("Open Date picker with range")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier.padding(all = 16.dp), onClick = {

                showTimePicker = true

            }) {
                Text("Open Time picker")
            }
        }
    }

    if (showDatePicker) {
        DatePickerModal(
            onDismiss = {
                showDatePicker = false
            },
            onDateSelected = {
                showDatePicker = false
            }
        )
    }

    if (showDatePickerWithInput) {
        DatePickerModal(
            displayMode = DisplayMode.Input,
            onDismiss = {
                showDatePickerWithInput = false
            },
            onDateSelected = {
                showDatePickerWithInput = false
            }
        )
    }

    if (showDatePickerRangeSelection) {
        DatePickerRangeModal(
            onDismiss = {
                showDatePickerRangeSelection = false
            },
            onDateRangeSelected = { datePair ->
                showDatePickerRangeSelection = false
            }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            onConfirmation = {
                showTimePicker = false
            },
            onDismiss = {
                showTimePicker = false
            }
        )
    }
}

@Composable
private fun DatePickerModal(
    modifier: Modifier = Modifier,
    displayMode: DisplayMode = DisplayMode.Picker,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {

    val datePickerState = rememberDatePickerState(initialDisplayMode = displayMode)

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(),
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState, showModeToggle = false)
    }
}

@Composable
private fun DatePickerRangeModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit
) {

    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(),
        confirmButton = {
            TextButton(onClick = {
                onDateRangeSelected(
                    Pair(
                        dateRangePickerState.selectedStartDateMillis,
                        dateRangePickerState.selectedEndDateMillis
                    )
                )
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Row(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(text = "Select date range")
                }
            },
            showModeToggle = false,
            modifier = modifier
        )
    }
}

@Composable
private fun TimePickerDialog(
    onConfirmation: () -> Unit,
    onDismiss: () -> Unit
) {

    val currentTime = java.util.Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(java.util.Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(java.util.Calendar.MINUTE),
        is24Hour = true
    )

    Dialog(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier.padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = onDismiss) {
                        Text("Dismiss picker")
                    }
                    Button(onClick = onConfirmation) {
                        Text("Confirm selection")
                    }
                }
            }
        }
    }
}