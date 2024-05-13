package com.example.androidReferences.loginApp.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun CustomTextBox(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: List<ImageVector>,
    label: String,
    placeholder: String,
    isPassword: Boolean = false,
) {

    // instruct textfield to track focus state
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // keep track of the hidden/reveal state of text
    val passVisibility = remember { mutableStateOf(false) }

    OutlinedTextField(
        interactionSource = interactionSource,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = if (isFocused) leadingIcon.first() else leadingIcon.last(),
                contentDescription = label,
                tint = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray
            )
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    passVisibility.value = !passVisibility.value
                }) {
                    Icon(
                        imageVector = if (passVisibility.value) Icons.Filled.Key else Icons.Filled.KeyOff,
                        contentDescription = "Show/Hide password",
                        tint = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        },

        visualTransformation = if (isPassword && !passVisibility.value) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },

        label = {
            Text(text = label)
        },

        placeholder = {
            Text(text = placeholder)
        }
    )
}
