package com.example.androidReferences.ui.components.utils

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


// dynamic upon focus/unfocus (color)
@Composable
fun LeadingIcon(
    isFocused: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
) {
    Icon(
        imageVector = if (isFocused) selectedIcon else unselectedIcon,
        contentDescription = "leading icon",
        tint = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray
    )
}
