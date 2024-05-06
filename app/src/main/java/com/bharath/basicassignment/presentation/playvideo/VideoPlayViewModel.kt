package com.bharath.basicassignment.presentation.playvideo

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.bharath.basicassignment.data.entity.VideoEntity
import com.bharath.basicassignment.domain.usecases.GetVideoDetailsById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayViewModel @Inject constructor(
    val player: ExoPlayer,
    savedStateHandle: SavedStateHandle,
    private val getVideoDetailsById: GetVideoDetailsById,

    ) : ViewModel() {


    private val _videoId = MutableStateFlow("video1")
    private val _videoDetails = MutableStateFlow(VideoEntity())
    val videoDetails = _videoDetails.asStateFlow()

    init {

        viewModelScope.launch {
            savedStateHandle.get<String>("videoId")?.let { id ->
                Log.d("Video", "Video id is :$id ")
                _videoId.update { id }

            }
        }
    }

    @OptIn(UnstableApi::class)
    fun initPlay(context: Context) {
        viewModelScope.launch {
            getVideoDetailsById(_videoId.value).filterNotNull().first()?.let { details ->
                _videoDetails.update { details }
                Log.d("VideoId", "initPlay: ${_videoDetails.value} ")

                val uri = details.videoUrl + ".mp4"
                val defaulDataSourceFactory = DefaultDataSource.Factory(context)
                val datasourceFactory = DefaultDataSource.Factory(
                    context, defaulDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(datasourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri.toUri()))
                player.setMediaSource(source)
                player.prepare()
                player.play()
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}