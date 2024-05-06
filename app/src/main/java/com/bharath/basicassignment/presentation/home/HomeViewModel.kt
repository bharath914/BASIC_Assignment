package com.bharath.basicassignment.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.basicassignment.data.entity.VideoEntity
import com.bharath.basicassignment.data.entity.doesMatchesQuery
import com.bharath.basicassignment.domain.usecases.GetVideoDetails
import com.bharath.basicassignment.domain.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getVideoDetails: GetVideoDetails,
) : ViewModel() {


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()


    private val _videoList = MutableStateFlow<RequestState<List<VideoEntity>>>(RequestState.Idle)
    val videoList = _videoList.asStateFlow()

    val filteredItems = searchText.onEach {

    }.combine(_videoList) { text, list ->
        if (text.isEmpty()) {
            emptyList()
        } else {

            list.getSuccessData().filter {
                it.doesMatchesQuery(text)
            }
        }

    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getVideoDetailsfromServer(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            getVideoDetails.invoke(isRefresh)
                .onEach { state ->
                    _videoList.update { state }
                }
                .collect()
        }
    }

    fun onEvent(events: HomeEvents) {
        viewModelScope.launch {

            when (events) {
                is HomeEvents.OnSearchTextChange -> {
                    _searchText.update { events.text }
                }

                is HomeEvents.OnRefresh -> {
                    launch(IO) {

                        _isRefreshing.update { true }
                        delay(1500)
                        getVideoDetailsfromServer(true)
                        _isRefreshing.update { false }
                    }

                }

                is HomeEvents.ClearSearch -> {
                    _searchText.update { "" }
                }
            }
        }
    }
}