package com.example.androidReferences.loginApp.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun CustomTextBox(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    label: String,
    placeholder: String,
    isPassword: Boolean = false,
    isOutline: Boolean = true,
    readOnly: Boolean = false,
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
            leadingIcon = { LeadingIcon(isFocused, label, selectedIcon, unselectedIcon) },
            trailingIcon = { PasswordTrailingIcon(isFocused, isPassword, passVisibility) },
            visualTransformation = textTransformation(isPassword, passVisibility),
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            readOnly = readOnly
        )
    } else {
        TextField(
            modifier = modifier,
            interactionSource = interactionSource,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            leadingIcon = { LeadingIcon(isFocused, label, selectedIcon, unselectedIcon) },
            trailingIcon = { PasswordTrailingIcon(isFocused, isPassword, passVisibility) },
            visualTransformation = textTransformation(isPassword, passVisibility),
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            readOnly = readOnly
        )
    }
}


// dynamic upon focus/unfocus (color)
@Composable
private fun LeadingIcon(
    isFocused: Boolean,
    label: String,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
) {
    Icon(
        imageVector = if (isFocused) selectedIcon else unselectedIcon,
        contentDescription = label,
        tint = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray
    )
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
