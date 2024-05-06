package com.bharath.basicassignment.data.repo

import com.bharath.basicassignment.data.LocalDao
import com.bharath.basicassignment.data.entity.VideoEntity
import com.bharath.basicassignment.domain.repo.local.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepoImpl @Inject constructor(
    private val dao: LocalDao,
) : LocalRepository {
    override suspend fun upsertVideoData(list: List<VideoEntity>) {
        withContext(Dispatchers.IO) {
            dao.upsertVideoData(list)
        }
    }

    override suspend fun getVideos(): Flow<List<VideoEntity>> {
        return withContext(Dispatchers.IO) {
            dao.getVideos()
        }
    }

    override suspend fun getVideoDataById(id: String): Flow<VideoEntity> {
        return withContext(Dispatchers.IO) {
            dao.getVideoDataById(id)
        }
    }
}