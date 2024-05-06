package com.bharath.basicassignment.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class VideoDataDTO(
    val id: String,
    val title: String,
    val imageUrl: String,
    val author: String,
    val videoLength: Long,

    )

@Serializable
@Keep
@Entity
data class VideoEntity(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val author: String = "", val videoLength: Long = 0,
    val videoUrl: String = "",
)


fun VideoEntity.doesMatchesQuery(query: String): Boolean {
    val combinations = listOf(
        id,
        title,
        author
    )
    return combinations.any {
        it.contains(query, true)
    }
}

@Serializable
@Keep
data class VideoDataWrapper(
    val videoDataDTO: VideoDataDTO,
    val videoUrl: String,
)

@Keep
@Serializable
data class VideoDetails(
    val title: String,
    val signedUrl: String,
)