package com.example.androidReferences.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.androidReferences.ui.components.utils.LeadingIcon


@Composable
fun CustomTextBox(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    label: String? = null,
    placeholder: String? = null,
    isPassword: Boolean = false,
    isOutline: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    // instruct textfield to track focus state
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // keep track of the hidden/reveal state of text
    val passVisibility = remember { mutableStateOf(false) }

    if (isOutline) {
        OutlinedTextField(
            modifier = modifier,
            interactionSource = interactionSource,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            leadingIcon = { LeadingIcon(isFocused, selectedIcon, unselectedIcon) },
            trailingIcon = { PasswordTrailingIcon(isFocused, isPassword, passVisibility) },
            visualTransformation = textTransformation(isPassword, passVisibility),
            label = {
                if (label != null) {
                    Text(text = label)
                }
            },
            placeholder = {
                if (placeholder != null) {
                    Text(text = placeholder)
                }
            },
            readOnly = readOnly,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        )
    } else {
        TextField(
            modifier = modifier,
            interactionSource = interactionSource,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            leadingIcon = { LeadingIcon(isFocused, selectedIcon, unselectedIcon) },
            trailingIcon = { PasswordTrailingIcon(isFocused, isPassword, passVisibility) },
            visualTransformation = textTransformation(isPassword, passVisibility),
            label = {
                if (label != null) {
                    Text(text = label)
                }
            },
            placeholder = {
                if (placeholder != null) {
                    Text(text = placeholder)
                }
            },
            readOnly = readOnly,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        )
    }
}


// dynamic upon pass toggle (Key/Keyoff) and focus/unfocus (color)
@Composable
private fun PasswordTrailingIcon(
    isFocused: Boolean,
    isPassword: Boolean,
    passVisibility: MutableState<Boolean>
) {
    if (isPassword) {
        IconButton(onClick = { passVisibility.value = !passVisibility.value }) {
            Icon(
                imageVector = if (passVisibility.value) Icons.Filled.Key else Icons.Filled.KeyOff,
                contentDescription = "Show/Hide password",
                tint = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
    }
}


// dynamic upon pass toggle (toggle reveal/hide text)
@Composable
private fun textTransformation(
    isPassword: Boolean,
    passVisibility: MutableState<Boolean>
): VisualTransformation {
    return if (isPassword && !passVisibility.value) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
}
