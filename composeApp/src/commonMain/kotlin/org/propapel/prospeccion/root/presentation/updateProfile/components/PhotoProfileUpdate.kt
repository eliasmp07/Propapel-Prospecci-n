package org.propapel.prospeccion.root.presentation.updateProfile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PhotoProfileUpdate(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    profile: String = "",
    isClickeable: Boolean = true,
    onClick: () -> Unit = {},
    image: ByteArray = ByteArray(0),
) {
    Box(modifier = modifier.size(110.dp)) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(
                    BorderStroke(
                        2.dp,
                        MaterialTheme.colorScheme.onBackground
                    ),
                    CircleShape
                )
                .clickable(
                    enabled = isClickeable
                ) {
                    onClick()
                }
        ) {
            if (image.isEmpty()){
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    model = profile ,
                    contentDescription = null
                )
            }else{
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    model = image ,
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.CameraAlt,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun PhotoProfileUpdateWindows(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    profile: String = "",
    isClickeable: Boolean = true,
    onClick: () -> Unit = {},
    image: ByteArray = ByteArray(0),
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(
                    BorderStroke(
                        2.dp,
                        MaterialTheme.colorScheme.onBackground
                    ),
                    CircleShape
                )
                .clickable(
                    enabled = isClickeable
                ) {
                    onClick()
                }
        ) {
            if (image.isEmpty()){
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(size - 20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    model = profile ,
                    contentDescription = null
                )
            }else{
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(size - 20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    model = image ,
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(size - 70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.CameraAlt,
                contentDescription = null,
            )
        }
    }
}