package com.bharath.basicassignment.data.repo

import android.util.Log
import com.bharath.basicassignment.data.entity.VideoDataDTO
import com.bharath.basicassignment.data.entity.VideoDetails
import com.bharath.basicassignment.domain.repo.remote.RemoteRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration

class RemoteRepoImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : RemoteRepository {

    val TAG = "VIDEOS"
    override suspend fun getVideoDetails(): List<VideoDataDTO> {

        return withContext(Dispatchers.IO) {
            val details =
                supabaseClient.postgrest["videodata"].select { }.decodeList<VideoDataDTO>()
            Log.d("Videos", "getVideoDetails:$details ")

            details.map {
                it
            }

            details
        }
    }

    override suspend fun getVideosFromStorage(): List<VideoDetails> {
        return emptyList()

    }

    private val storage = supabaseClient.storage.from("videos")
    override suspend fun getVideoUrlByVideoId(videoId: String): String {
        return storage.publicUrl(videoId)
    }
}
