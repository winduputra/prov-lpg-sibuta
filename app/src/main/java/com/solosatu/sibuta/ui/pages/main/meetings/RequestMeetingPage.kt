package com.solosatu.sibuta.ui.pages.main.meetings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.R
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@Composable
fun RequestMeetingPage() {
    var txtSearch by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Daftar Tamu", fontSize = 28.sp, fontWeight = FontWeight.Medium)
            Image(
                painter = painterResource(id = R.drawable.ic_app),
                contentDescription = "icon app",
                modifier = Modifier.size(87.dp)
            )
        }
        Gap(20.dp)
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            value = txtSearch,
            onValueChange = { txtSearch = it },
            shape = CircleShape,
            prefix = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    painter = painterResource(id = R.drawable.round_search),
                    contentDescription = "search"
                )
            },
            placeholder = {
                Text(text = "Search", color = MaterialTheme.colorScheme.outline)
            }
        )
        FilterMeetingType(
            modifier = Modifier.padding(20.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
        ) {
            items(10) {
                GuestMeetingItem()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SIBUTATheme {
        RequestMeetingPage()
    }
}