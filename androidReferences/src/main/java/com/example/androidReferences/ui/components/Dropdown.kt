package com.example.androidReferences.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(options[0]) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {

        TextField(
            modifier = Modifier.menuAnchor(),
            interactionSource = interactionSource,
            value = selectedText,
            onValueChange = {},
            label = { Text(text = label) },
            leadingIcon = { LeadingIcon(isFocused, selectedIcon, unselectedIcon) },
            readOnly = true
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            options.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = { Text(text = text) },
                    onClick = {
                        selectedText = options[index]
                        isExpanded = false
                        onValueChange(selectedText) // trigger the state update
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
