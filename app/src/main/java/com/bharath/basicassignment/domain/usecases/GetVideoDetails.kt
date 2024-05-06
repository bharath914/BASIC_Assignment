package com.bharath.basicassignment.domain.usecases

import com.bharath.basicassignment.data.entity.VideoDataDTO
import com.bharath.basicassignment.data.entity.VideoEntity
import com.bharath.basicassignment.domain.repo.local.LocalRepository
import com.bharath.basicassignment.domain.repo.remote.RemoteRepository
import com.bharath.basicassignment.domain.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVideoDetails @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
) {

    operator fun invoke(isRefresh: Boolean = false): Flow<RequestState<List<VideoEntity>>> = flow {
        try {
            emit(RequestState.Loading)
            val localList = localRepository.getVideos().filterNotNull().first()
            if (isRefresh || localList.isEmpty()) {

                val res = remoteRepository.getVideoDetails()
                insert(res)
                val newList = localRepository.getVideos().filterNotNull().first()
                emit(RequestState.Success(newList))
            } else {
                emit(RequestState.Success(localList))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(RequestState.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

    private suspend fun insert(list: List<VideoDataDTO>) {

        val mapped = list.map {
            val storageUrl = remoteRepository.getVideoUrlByVideoId(it.id)
            VideoEntity(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                author = it.author,
                videoLength = it.videoLength,
                videoUrl = storageUrl

            )
        }
        localRepository.upsertVideoData(mapped)
    }
}

