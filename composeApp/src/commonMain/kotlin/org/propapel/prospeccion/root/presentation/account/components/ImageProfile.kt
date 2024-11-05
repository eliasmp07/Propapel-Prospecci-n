package org.propapel.prospeccion.root.presentation.account.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.banner_profile

@Composable
fun ImageProfile(
    modifier: Modifier = Modifier,
    profileImg: String = "",
    isAdmin: Boolean = false
){
    Box(
        modifier = modifier.aspectRatio(16f / 9f).fillMaxWidth()
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.banner_profile),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.aspectRatio(16f / 7f)
        )
        Box(modifier = modifier.size(150.dp).align(Alignment.BottomStart).padding(10.dp)) {
            if (profileImg.isNotEmpty()){
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(
                                2.dp,
                                MaterialTheme.colorScheme.onBackground
                            ),
                            CircleShape
                        ).background(Color.White)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background),
                        contentScale = ContentScale.Crop,
                        model = profileImg,
                        contentDescription = null
                    )
                }
            }else{
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(
                                2.dp,
                                MaterialTheme.colorScheme.onBackground
                            ),
                            CircleShape
                        ).background(Color.White)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background),
                        contentScale = ContentScale.Crop,
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }
            }
            if(isAdmin){
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.WorkspacePremium,
                        tint = Color.Yellow,
                        contentDescription = null,
                    )
                }
            }

        }
    }
}