package com.solosatu.sibuta.ui.pages.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.R
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@Composable
fun ProfilePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffA259FF))
    ) {
        Image(painter = painterResource(id = R.drawable.ic_bg_top), contentDescription = "")
        Box {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp, start = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_app),
                    contentDescription = "app",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.TopEnd)
                )
                Row(
                    modifier = Modifier.padding(top = 52.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .padding(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_person_24),
                            contentDescription = "profile_pict",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Gap(20.dp)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Nama PNS", color = Color.White, fontWeight = FontWeight.Medium)
                        Text(
                            text = "Jabatan PNS",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Ganti Password".uppercase(), fontSize = 20.sp, modifier = Modifier.padding(vertical = 10.dp))
            }
            Gap(20.dp)
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Ubah Foto".uppercase(), fontSize = 20.sp, modifier = Modifier.padding(vertical = 10.dp))
            }
            Gap(20.dp)
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Logout".uppercase(), fontSize = 20.sp, modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SIBUTATheme {
        ProfilePage()
    }
}