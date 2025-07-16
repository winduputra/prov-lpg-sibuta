package com.solosatu.sibuta.ui.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.solosatu.sibuta.R
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(
    isVisible: Boolean = false,
) {
    if (isVisible){
        AlertDialog(onDismissRequest = { }) {
            Surface(
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
                        R.raw.lottie_loading_circle))
                    LottieAnimation(
                        modifier = Modifier
                            .requiredSize(72.dp)
                            .scale(2f),
                        composition = composition,
                        renderMode = RenderMode.AUTOMATIC,
                        iterations = LottieConstants.IterateForever,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Mohon Tunggu ...", color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SIBUTATheme {
        LoadingDialog(
            isVisible = true
        )
    }
}
