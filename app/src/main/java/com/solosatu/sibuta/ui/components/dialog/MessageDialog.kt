package com.solosatu.sibuta.ui.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDialog(
    isVisible: Boolean = false,
    title: String = "Title",
    description: String = "desription",
    onDismiss: () -> Unit = {}
) {
    if (isVisible) {
        AlertDialog(onDismissRequest = { }) {
            Surface(
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 32.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Gap(20.dp)

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDismiss
                    ) {
                        Text(text = "Mengerti", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SIBUTATheme {
        MessageDialog(isVisible = true)
    }
}
