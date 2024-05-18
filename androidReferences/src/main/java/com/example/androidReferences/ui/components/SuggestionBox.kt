package com.example.androidReferences.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.androidReferences.Constant.countries


@Composable
fun SuggestionBox(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var option by remember { mutableStateOf("") }
    val heightTextFields by remember { mutableStateOf(55.dp) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    isExpanded = false
                }
            )
    ) {
        TextField(
            modifier = Modifier
                .height(heightTextFields)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .onFocusChanged {
                    isExpanded = it.isFocused
                },
            value = option,
            onValueChange = {
                option = it
                isExpanded = true
            },
            trailingIcon = {
                TrailingIcon(
                    value = option,
                    expanded = isExpanded,
                    onExpandChange = { isExpanded = it },
                    onValueReset = {
                        option = ""
                    },
                )
            }
        )
    }

    AnimatedVisibility(visible = isExpanded) {
        Card(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .width(textFieldSize.width.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            LazyColumn(
                modifier = Modifier.heightIn(max = 200.dp),
            ) {
                if (option.isNotEmpty()) {

                    items(
                        countries.filter { it.lowercase().startsWith(option.lowercase()) }.sorted()
                    ) {
                        OptionItem(title = it) { title ->
                            option = title
                            isExpanded = false
                            onValueChange(option)
                        }
                    }
                } else {
                    items(countries.sorted()) {
                        OptionItem(title = it) { title ->
                            option = title
                            isExpanded = false
                            onValueChange(option)
                        }
                    }
                }
            }
        }

    }
}


@Composable
private fun OptionItem(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}


@Composable
private fun TrailingIcon(
    value: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onValueReset: () -> Unit,
) {
    if (value.isBlank()) {
        IconButton(
            onClick = { onExpandChange(!expanded) }
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = "arrow",
                tint = Color.Black
            )
        }
    } else {
        IconButton(
            onClick = {
                onExpandChange(false)
                onValueReset()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Cancel,
                contentDescription = "cancel",
                tint = Color.Black
            )
        }
    }
}
