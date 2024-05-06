package com.bharath.basicassignment.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharath.basicassignment.presentation.home.HomeEvents
import com.bharath.basicassignment.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(homeViewModel: HomeViewModel, onNavigate: (id: String) -> Unit) {
    val onEvent: (event: HomeEvents) -> Unit = remember {
        return@remember homeViewModel::onEvent
    }
    val filteredItems by homeViewModel.filteredItems.collectAsStateWithLifecycle()
    val isActive = remember {
        mutableStateOf(false)
    }
    val cardMod = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    val text by homeViewModel.searchText.collectAsStateWithLifecycle()
    SearchBar(
        query = text,
        onQueryChange = {
            onEvent(HomeEvents.OnSearchTextChange(it))
        },
        onSearch = {},
        active = isActive.value,
        onActiveChange = {
            isActive.value = !isActive.value
        },
        placeholder = {
            Text(text = "Search for title, channel", style = MaterialTheme.typography.bodyLarge)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
        },
        trailingIcon = {
            if (isActive.value) {

                IconButton(onClick = {
                    onEvent(HomeEvents.ClearSearch)
                    isActive.value = !isActive.value
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                }
            }
        }
    ) {
        LazyColumn {
            items(filteredItems) {
                VideoCard(
                    modifier = cardMod, data = it
                ) {
                    onNavigate(it.id)
                }
            }

        }
    }
}