package com.bharath.basicassignment.presentation.playvideo

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController

@Composable
fun VideoPlayerScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<VideoPlayViewModel>()
    Content(navHostController = navHostController, viewModel)
}

@OptIn(UnstableApi::class)
@Composable
private fun Content(
    navHostController: NavHostController,
    viewModel: VideoPlayViewModel,
) {


    val videoDetails by viewModel.videoDetails.collectAsStateWithLifecycle()
    val exoplayer = remember {
        viewModel.player
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {

        viewModel.initPlay(context)
    }
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoplayer.release()
        }
    }
    val modifier = Modifier.padding(horizontal = 16.dp)
    Scaffold(

    ) { innerPad ->
        Column(
            modifier = Modifier
                .padding(innerPad)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            AndroidView(
                factory = {
                    PlayerView(it).apply {
                        useController = true
                        this.setShowNextButton(false)
                        this.setShowPreviousButton(false)
                        this.controllerAutoShow = false

                        player = exoplayer
                        this.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    }
                }, update = {
                    when (lifecycle) {
                        Lifecycle.Event.ON_PAUSE -> {
                            it.onPause()
                            it.player?.pause()
                        }

                        Lifecycle.Event.ON_RESUME -> {
                            it.onResume()
                        }

                        else -> Unit
                    }
                }, modifier = Modifier
                    .aspectRatio(16f / 9f)

            )
            Text(
                text = videoDetails.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            Text(
                text = videoDetails.author,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
            )
        }
    }


}