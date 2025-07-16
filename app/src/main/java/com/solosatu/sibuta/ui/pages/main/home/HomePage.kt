package com.solosatu.sibuta.ui.pages.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.solosatu.sibuta.R
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.components.RatingStar
import com.solosatu.sibuta.ui.theme.SIBUTATheme
import com.solosatu.sibuta.ui.theme.waitingColor

@Composable
fun HomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffA259FF))
    ) {
        Image(painter = painterResource(id = R.drawable.ic_bg_top), contentDescription = "")

        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = "app",
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(160.dp)
        )
        Column(
            modifier = Modifier
                .padding(top = 200.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    )
                )
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tamu Hari Ini",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Gap(8.dp)
                    Text(
                        text = "10",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
                Gap(20.dp)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tamu Minggu Ini",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Gap(8.dp)
                    Text(
                        text = "10",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Gap(20.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rating Pelayanan Anda",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
                Gap(8.dp)
                RatingStar(
                    rating = 3.5f,
                    color = waitingColor,
                    size = 32.dp,
                    onStarClick = {}
                )
                Text(
                    text = "(3.5)",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
            Gap(20.dp)
            Button(onClick = { }) {
                Text(text = "Tahun: 2024")
                Icon(Icons.Rounded.KeyboardArrowDown, contentDescription = "arrow")
            }
            Gap(10.dp)
            Chart(
                chart = lineChart(),
                model = entryModelOf(Pair(1, 20), Pair(2, 1), Pair(3, 5)),
                startAxis = rememberStartAxis(
                    label = axisLabelComponent(color = MaterialTheme.colorScheme.primary)
                ),
                bottomAxis = rememberBottomAxis(
                    label = axisLabelComponent(color = MaterialTheme.colorScheme.primary)
                ),
            )
            Gap(20.dp)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(text = "Download Laporan")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SIBUTATheme {
        HomePage()
    }
}