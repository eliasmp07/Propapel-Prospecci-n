@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo

@Composable
fun CustomTopAppBar(
    windowSizeClass: WindowSizeClass,
    user: AuthInfo,
    totalNotifications: Int,
    onLogout: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    editProfile: () -> Unit,
    isProfile: Boolean = false,
    profileImage: String = "",
    onDarkTheme: () -> Unit = {}
) {
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    var expanded by remember { mutableStateOf(false) }
    var expandedCompact by remember { mutableStateOf(false) }

    if (isCompact) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryYellowLight,
                scrolledContainerColor = PrimaryYellowLight
            ),
            scrollBehavior = scrollBehavior,
            title = {

            },
            actions = {
                Card(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFe6f0f9)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        BadgedBox(badge = {
                            if (totalNotifications > 0) {
                                Badge {
                                    Text(text = totalNotifications.toString())
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                AnimatedVisibility(
                    visible = !isProfile
                ){
                    Box(
                        modifier = Modifier.clickable {
                            expandedCompact = !expandedCompact
                        }
                    ) {
                        Box(
                            modifier = Modifier.size(35.dp).clickable {
                                expandedCompact = !expandedCompact
                            },
                            contentAlignment = Alignment.Center
                        ) {
                            if (profileImage.isNotBlank()){
                                AsyncImage(
                                    model = profileImage,
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(8.dp))
                                        .pointerHoverIcon(PointerIcon.Hand)
                                )
                            }else{
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                                        .align(Alignment.Center)
                                        .fillMaxSize().background(Color.White)
                                ){
                                    Image(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = "Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(8.dp))
                                            .pointerHoverIcon(PointerIcon.Hand)
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(Color.Green, CircleShape)
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-2).dp, y = 2.dp)
                            )
                        }
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            expanded = expandedCompact,
                            onDismissRequest = { expandedCompact = !expandedCompact }) {
                            DropdownMenuItem(
                                text = {
                                    Text("${user.name} ${user.lastname}")
                                },
                                onClick = {
                                    expandedCompact = !expandedCompact
                                },
                                leadingIcon = {
                                    if (profileImage.isNotBlank()){
                                        AsyncImage(
                                            model = profileImage,
                                            contentDescription = "Image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .pointerHoverIcon(PointerIcon.Hand)
                                        )
                                    }else{
                                        Box(
                                            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                                                .size(50.dp).background(Color.White)
                                        ){
                                            Image(
                                                imageVector = Icons.Outlined.Person,
                                                contentDescription = "Image",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(50.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .pointerHoverIcon(PointerIcon.Hand)
                                            )
                                        }
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Editar perfil") },
                                onClick = {
                                    expandedCompact = !expandedCompact
                                    editProfile()
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                onClick = {
                                    expandedCompact = !expandedCompact
                                    onLogout()
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Logout,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth().animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .background(Color(0XFFf1f4f9))
                    .padding(6.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "ProSales",
                    color = Color(0xFF007BFF),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                )

                Spacer(modifier = Modifier.weight(4f))

                Card(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFe6f0f9)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LightMode,
                            contentDescription = "Light Mode",
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand).clickable {
                                onDarkTheme()
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                val item = 4
                Card(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFe6f0f9)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        BadgedBox(badge = {
                            if (item > 0) {
                                Badge {
                                    Text(text = item.toString())
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))
                AnimatedVisibility(
                    visible = !isProfile
                ){
                    Box(
                        modifier = Modifier.size(35.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = profileImage,
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                                .pointerHoverIcon(PointerIcon.Hand)
                        )
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color.Green, CircleShape)
                                .align(Alignment.TopEnd)
                                .offset(x = (-2).dp, y = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Elias Mena",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = null
                            )
                        }

                    }
                    DropdownMenu(
                        modifier = Modifier.align(Alignment.TopEnd),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Editar perfil") },
                            onClick = {
                                expanded = false
                                editProfile()
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar sesión") },
                            onClick = {
                                expanded = false
                                onLogout()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Logout,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

        }
    }

}