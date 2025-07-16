package com.solosatu.sibuta.ui.pages.main.meetings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.R
import com.solosatu.sibuta.enums.MeetingType
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
fun FilterMeetingType(
    modifier: Modifier = Modifier,
    currentType: MeetingType = MeetingType.Permintaan_Pelayanan,
    onChangeFilter: (type: MeetingType) -> Unit = {}
){
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MeetingType.entries.forEach {type->
            FilterChip(
                selected = currentType == type,
                onClick = {
                  onChangeFilter(type)
                }, label = {
                    if (currentType == type){
                        Icon(painter = painterResource(id = R.drawable.round_check), contentDescription = type.name, modifier = Modifier.size(14.dp))
                        Gap(8.dp)
                    }
                    Text(text = type.name.replace("_", " "), fontSize = 12.sp)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Priview(){
    SIBUTATheme {
        FilterMeetingType()
    }
}