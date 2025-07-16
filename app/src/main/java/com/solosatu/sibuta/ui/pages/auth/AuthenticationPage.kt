package com.solosatu.sibuta.ui.pages.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.helper.clickableNoRipple
import com.solosatu.sibuta.helper.shadow
import com.solosatu.sibuta.ui.components.Gap
import com.solosatu.sibuta.ui.components.PasswordTextField
import com.solosatu.sibuta.ui.pages.main.MainActivity
import com.solosatu.sibuta.ui.theme.SIBUTATheme

class AuthenticationPage : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SIBUTATheme {
                AuthenticationView()
            }
        }
    }

}

@Composable
private fun AuthenticationView() {
    val context = LocalContext.current

    var nameText by remember { mutableStateOf("") }
    var isErrorNameActive by remember { mutableStateOf(false) }

    var passwordTxt by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isErrorPasswordActive by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val shape = RoundedCornerShape(10.dp)
            Card(
                modifier = Modifier
                    .padding(14.dp)
                    .shadow(
                        offsetX = 4.dp,
                        offsetY = 4.dp,
                        blurRadius = 4.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = shape,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Masuk",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Gap(20.dp)
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = nameText,
                        shape = RoundedCornerShape(8.dp),
                        onValueChange = { nameText = it },
                        singleLine = true,
                        isError = isErrorNameActive && nameText.isEmpty(),
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.AccountCircle,
                                "icAccount",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Username",
                                color = MaterialTheme.colorScheme.outline.copy(0.6f)
                            )
                        },
                        supportingText = {
                            if (isErrorNameActive && nameText.isEmpty()) {
                                Text(
                                    text = "Mohon diisi",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    )
                    Gap(10.dp)
                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = "Password",
                        onValueChange = { value, isValid ->
                            passwordTxt = value
                            isPasswordValid = isValid
                        },
                        isErrorActive = isErrorPasswordActive
                    )
//                    Text(
//                        text = "Lupa Password?",
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 14.sp,
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier
//                            .align(Alignment.End)
//                            .clickableNoRipple {
//
//                            }
//                    )
                    Gap(20.dp)
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            context.startActivity(Intent(context, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            })
                        }
                    ) {
                        Text("Masuk", fontWeight = FontWeight.SemiBold)
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
        AuthenticationView()
    }
}