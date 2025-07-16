package com.solosatu.sibuta.ui.pages.main.meetings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.R
import com.solosatu.sibuta.ui.components.DashLine
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.components.dialog.ConfirmationDialog
import com.solosatu.sibuta.ui.pages.main.components.ChangeDateTimeDialog
import com.solosatu.sibuta.ui.theme.SIBUTATheme
import com.solosatu.sibuta.ui.theme.successColor
import com.solosatu.sibuta.ui.theme.waitingColor

@Composable
fun GuestMeetingItem(
    onAccepted: () -> Unit = {},
    onRejected: () -> Unit = {},
    onChangeDate: (date: String, time: String) -> Unit = { _, _ -> }
) {
    var isAcceptConfirmationShow by remember { mutableStateOf(false) }
    var isRejectConfirmationShow by remember { mutableStateOf(false) }
    var isChangeDateShow by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_person_24),
                    contentDescription = "account",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Agus")
                    Gap(10.dp)
                    Text(
                        modifier = Modifier
                            .background(
                                color = waitingColor,
                                shape = CircleShape
                            )
                            .padding(horizontal = 10.dp, vertical = 2.dp),
                        text = "Penyedia",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = "Permintaan Layanan",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.background
                )
                Gap(10.dp)
                Text(
                    fontSize = 14.sp,
                    text = "12 Agustus 2023 18:00 WIB"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElevatedButton(
                        modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            isAcceptConfirmationShow = true
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = successColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_check),
                            contentDescription = "accept",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Gap(10.dp)
                    ElevatedButton(
                        modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            isRejectConfirmationShow = true
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_close),
                            contentDescription = "reject",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Gap(10.dp)
                    ElevatedButton(
                        modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            isChangeDateShow = true
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = waitingColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_date),
                            contentDescription = "re-schedule",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "Keterangan pertemuan",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.outline
        )
        Gap(10.dp)
        DashLine()
        Gap(10.dp)

    }

    ConfirmationDialog(
        isVisible = isAcceptConfirmationShow || isRejectConfirmationShow,
        title = if (isAcceptConfirmationShow) "Anda akan menerima undangan ini?" else "Anda akan menolak undangan ini?",
        onConfirm = {
            if (isAcceptConfirmationShow) onAccepted()
            else if (isRejectConfirmationShow) onRejected()

            isAcceptConfirmationShow = false
            isRejectConfirmationShow = false
        },
        onCancel = {
            isAcceptConfirmationShow = false
            isRejectConfirmationShow = false
        }
    )

    ChangeDateTimeDialog(
        isVisible = isChangeDateShow,
        onDone = { data, time ->
            isChangeDateShow = false
            onChangeDate(data, time)
        }, onCancel = { isChangeDateShow = false }
    )
}

@Preview
@Composable
private fun Preview() {
    SIBUTATheme {
        GuestMeetingItem()
    }
}