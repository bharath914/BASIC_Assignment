package com.bharath.basicassignment.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bharath.basicassignment.presentation.Screens
import com.bharath.basicassignment.presentation.components.SearchBox
import com.bharath.basicassignment.presentation.components.VideoCard

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    Content(navHostController = navHostController, homeViewModel = viewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
) {
    val cardMod = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    LaunchedEffect(key1 = true) {
        homeViewModel.getVideoDetailsfromServer()
    }

    val onNavigate: (id: String) -> Unit = remember {
        return@remember { id ->
            navHostController.navigate(Screens.VideoPlayer.route + "/$id")
        }
    }
    val onEvent: (event: HomeEvents) -> Unit = remember {
        return@remember homeViewModel::onEvent
    }
    val isRefreshing by homeViewModel.isRefreshing.collectAsStateWithLifecycle()

    val videoListState by homeViewModel.videoList.collectAsStateWithLifecycle()


    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onEvent(HomeEvents.OnRefresh) }
    )
    videoListState.DisplayResult(onLoading = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LinearProgressIndicator()
        }
    }, onSuccess = { list ->

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SearchBox(homeViewModel = homeViewModel) { id ->
                onNavigate(id)
            }

            Box(
                modifier = Modifier.pullRefresh(pullRefreshState),
                contentAlignment = Alignment.TopCenter
            ) {


                LazyColumn {

                    items(list, key = {
                        it.id
                    }) {
                        VideoCard(modifier = cardMod, data = it) {
                            onNavigate(it.id)
                        }
                    }
                }
                PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState)
            }
        }
    }) {

    }

}


