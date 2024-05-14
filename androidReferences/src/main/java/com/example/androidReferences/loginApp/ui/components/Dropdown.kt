package com.example.androidReferences.loginApp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    options: List<String>,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        CustomTextBox(
            modifier = Modifier.menuAnchor(),
            label = label,
            value = selectedText,
            onValueChange = onValueChange,
            selectedIcon = selectedIcon,
            unselectedIcon = unselectedIcon,
            isOutline = false,
            placeholder = placeholder,
            readOnly = true
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            options.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = { Text(text = text) },
                    onClick = {
                        selectedText = options[index]
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
