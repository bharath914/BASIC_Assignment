package com.bharath.basicassignment.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bharath.basicassignment.data.entity.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
    @Upsert
    suspend fun upsertVideoData(list: List<VideoEntity>)

    @Query("select * from VideoEntity")
    fun getVideos(): Flow<List<VideoEntity>>

    @Query("select * from VideoEntity where id=:id")
    fun getVideoDataById(id: String): Flow<VideoEntity>
}