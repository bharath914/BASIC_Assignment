package com.bharath.basicassignment.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bharath.basicassignment.data.entity.VideoEntity
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoCard(
    modifier: Modifier,
    data: VideoEntity,
    onclick: () -> Unit,
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onclick
    ) {


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            AsyncImage(
                model = data.imageUrl,
                contentDescription = "Thumbnail of youtube video",
                modifier = Modifier.aspectRatio(16f / 9f),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = data.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = data.author,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f),

                    )
//                Text(
//                    text = formatTime(data.videoLength),
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
//                )

            }

        }


    }

}

fun formatTime(time: Long): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault()).format(time)
    return timeFormat
}