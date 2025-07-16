package com.solosatu.sibuta.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solosatu.sibuta.R
import com.solosatu.sibuta.helper.hasEnoughDigits
import com.solosatu.sibuta.helper.isLongEnough
import com.solosatu.sibuta.helper.isMixedCase
import com.solosatu.sibuta.helper.isValidPassword
import com.solosatu.sibuta.helper.notContainRepetitiveNumber
import com.solosatu.sibuta.ui.theme.SIBUTATheme

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (value: String, isValid: Boolean) -> Unit = { _, _ -> },
    shape: Shape = RoundedCornerShape(8.dp),
    placeholder: String = "Password",
    isErrorActive: Boolean = false,
    alterError: String = ""
) {
    var passwordTxt by remember { mutableStateOf("") }
    val isValidPassword by remember {
        derivedStateOf { passwordTxt.isValidPassword }
    }
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(passwordTxt) {
        onValueChange(passwordTxt, isValidPassword)
    }

    OutlinedTextField(
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.outline.copy(0.6f)
            )
        },
        value = passwordTxt,
        onValueChange = { passwordTxt = it },
        shape = shape,
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = isValidPassword.not() && isErrorActive,
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_key),
                "icPassword",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(32.dp)
            )
        },
        trailingIcon = {
            val icon: Int
            val description: String
            if (isPasswordVisible) {
                icon = R.drawable.ic_eye_open
                description = "passwordVisible"
            } else {
                icon = R.drawable.ic_eye_close
                description = "passwordInvisible"
            }

            Icon(
                painterResource(icon),
                description,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                ))
        },
        supportingText = {
            if ((isValidPassword.not() || alterError.isNotEmpty()) && isErrorActive) {
                val textError = when {
                    passwordTxt.isLongEnough().not() -> "Password minimal 8 karakter"
                    passwordTxt.hasEnoughDigits().not() -> "Password harus mengandung angka"
                    passwordTxt.isMixedCase()
                        .not() -> "Password harus mengandung huruf besar & kecil"

                    passwordTxt.notContainRepetitiveNumber()
                        .not() -> "Password tidak boleh ada angka yang berurutan sama"

                    else -> alterError
                }
                Text(
                    text = textError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SIBUTATheme {
        PasswordTextField()
    }
}
