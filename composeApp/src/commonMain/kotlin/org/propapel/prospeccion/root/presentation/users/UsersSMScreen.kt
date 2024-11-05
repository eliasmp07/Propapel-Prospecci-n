package org.propapel.prospeccion.root.presentation.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.propapel.prospeccion.root.domain.repository.User

@Composable
fun UsersSMScreenRoot(
    viewModel: UserSMViewModel,
    onAddUser: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    UsersSMScreen(
        state  = state,
        onAddUser = onAddUser
    )
}

@Composable
private fun UsersSMScreen(
    state: UsersSMState,
    onAddUser: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Usuarios",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF007BFF)
                    )
                }
            }
            items(state.users) {
                ItemUser(it)
            }
        }
        ExtendedFloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            icon = {
                Icon(
                    imageVector = Icons.Filled.PersonAddAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            onClick = {
            },
            text = {
                Text(text = "Agregar usuario")
            }
        )
    }
}

@Composable
fun ItemUser(
    user: User
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.padding(16.dp).size(50.dp),
                contentAlignment = Alignment.Center
            ) {
                if (user.image.isEmpty()){
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                            .align(Alignment.Center)
                            .size(40.dp).background(Color.White)
                    ){
                        Image(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .pointerHoverIcon(PointerIcon.Hand)
                        )
                    }
                }else{
                    AsyncImage(
                        model = user.image,
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(8.dp))
                            .pointerHoverIcon(PointerIcon.Hand)
                    )
                }
                if (user.isAdmin){
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-2).dp, y = 2.dp),
                        imageVector = Icons.Outlined.WorkspacePremium,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                }

            }
            Column {
                Text("${user.name} ${user.lastname}", style = MaterialTheme.typography.titleSmall, color = Color.White)
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(text = user.phone, style = MaterialTheme.typography.titleSmall, color = Color.White)
            }
            Spacer(
                modifier = Modifier.weight(0.3f)
            )
            Text(text = if(user.isAdmin) "Es admin" else  "No es admin",  style = MaterialTheme.typography.titleSmall, color = Color.White)
            Spacer(
                modifier = Modifier.width(8.dp)
            )
        }
    }
}