package com.example.androidReferences.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.androidReferences.Constant.countries


// To be added
@Composable
fun AutoSuggestionBox(
    onValueChange: (String) -> Unit
) {
    var country by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightTextFields)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            value = country,
            onValueChange = {
                country = it
                expanded = true
            },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "arrow",
                        tint = Color.Black
                    )
                }
            }
        )
    }

    AnimatedVisibility(visible = expanded) {
        Card(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .width(textFieldSize.width.dp),
            shape = RoundedCornerShape(10.dp)
        ) {

            LazyColumn(
                modifier = Modifier.heightIn(max = 100.dp),
            ) {

                if (country.isNotEmpty()) {
                    items(
                        countries.filter {
                            it.lowercase().contains(country.lowercase()) || it.lowercase().contains("others")
                        }.sorted()
                    ) {
                        CountryItems(title = it) { title ->
                            country = title
                            expanded = false
                        }
                    }
                } else {
                    items(countries.sorted()) {
                        CountryItems(title = it) { title ->
                            country = title
                            expanded = false
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun CountryItems(
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
