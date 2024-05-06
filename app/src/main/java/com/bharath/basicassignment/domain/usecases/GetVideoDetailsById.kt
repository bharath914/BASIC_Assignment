package com.bharath.basicassignment.domain.usecases

import com.bharath.basicassignment.data.entity.VideoEntity
import com.bharath.basicassignment.domain.repo.local.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoDetailsById @Inject constructor(
    private val localRepository: LocalRepository,
) {
    suspend operator fun invoke(videoId: String): Flow<VideoEntity> =
        localRepository.getVideoDataById(videoId)
}