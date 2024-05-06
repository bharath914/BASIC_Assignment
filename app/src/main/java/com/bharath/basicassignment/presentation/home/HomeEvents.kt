package com.bharath.basicassignment.presentation.home

sealed class HomeEvents {
    data class OnSearchTextChange(val text:String): HomeEvents()
    data object OnRefresh:HomeEvents()
    data object ClearSearch:HomeEvents()
}
