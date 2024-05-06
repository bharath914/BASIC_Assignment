package com.bharath.basicassignment.domain.repo.remote

import com.bharath.basicassignment.data.entity.VideoDataDTO
import com.bharath.basicassignment.data.entity.VideoDetails

interface RemoteRepository {

    suspend fun getVideoDetails(): List<VideoDataDTO>

    suspend fun getVideosFromStorage(): List<VideoDetails>
    suspend fun getVideoUrlByVideoId(videoId: String): String
}