package com.example.androidReferences.apps.retrofitApp.presentation.allCountries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun CountriesScreen(
    viewModel: CountriesViewModel, modifier: Modifier = Modifier,
) {
    val myCountries by viewModel.state.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.getCountries() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Get Countries")
        }

        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(myCountries.countries.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(Random.nextLong())),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = myCountries.countries[index].cca2.toString())
                }
            }
        }
    }
}
