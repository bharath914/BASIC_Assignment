package com.bharath.basicassignment.domain.repo.local

import com.bharath.basicassignment.data.entity.VideoEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun upsertVideoData(list: List<VideoEntity>)

    suspend fun getVideos():Flow<List<VideoEntity>>
    suspend fun getVideoDataById(id:String):Flow<VideoEntity>
}