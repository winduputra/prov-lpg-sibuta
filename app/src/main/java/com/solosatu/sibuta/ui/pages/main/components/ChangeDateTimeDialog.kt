package com.solosatu.sibuta.ui.pages.main.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.R
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeDateTimeDialog(
    isVisible: Boolean = false,
    date: String = "2024-01-23",
    time: String = "18:00",
    onDone: (date: String, time: String) -> Unit = { _, _ -> },
    onCancel: () -> Unit = {}
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(date) }
    var selectedTime by remember { mutableStateOf(time) }

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            selectedDate = "${year.toString().padStart(2, '0')}-${
                month.toString().padStart(2, '0')
            }-${day.toString().padStart(2, '0')}"
        },
        selectedDate.split("-")[0].toInt(),
        selectedDate.split("-")[1].toInt(),
        selectedDate.split("-")[2].toInt()
    )

    val timePicker = TimePickerDialog(
        context,
        { _, hour, minute ->
            selectedTime =
                "${hour.toString().padStart(0, '0')}:${minute.toString().padStart(2, '0')}"
        },
        selectedTime.split(":")[0].toInt(), selectedTime.split(":")[1].toInt(), true
    )

    if(isVisible){
        AlertDialog(
            onDismissRequest = onCancel,
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ubah Waktu Pertemuan",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Gap(20.dp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1.4f)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    datePicker.show()
                                }
                                .padding(horizontal = 10.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Rounded.DateRange,
                                contentDescription = "date",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Gap(10.dp)
                            Text(
                                text = selectedDate,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Gap(20.dp)
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    timePicker.show()
                                }
                                .padding(horizontal = 10.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_time),
                                contentDescription = "date",
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Gap(10.dp)
                            Text(
                                text = selectedTime,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Gap(20.dp)
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = { onCancel() }
                        ) {
                            Text(text = "Batal")
                        }
                        Gap(20.dp)
                        Button(onClick = {
                            onDone(selectedDate, selectedTime)
                        }) {
                            Text(text = "Ganti")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SIBUTATheme {
        ChangeDateTimeDialog(
            isVisible = true
        )
    }
}