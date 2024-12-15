@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Menu
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.root.domain.models.Reminder
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_ref
import prospeccion.composeapp.generated.resources.logo

fun converteDate(date: Long): String {
    val dateConverte = Instant.fromEpochMilliseconds(date).toLocalDateTime(TimeZone.UTC).date
    return "${dateConverte.dayOfMonth}/${dateConverte.monthNumber}/${dateConverte.year}"
}

@Composable
fun CustomTopAppBar(
    windowSizeClass: WindowSizeClass,
    user: AuthInfo,
    reminders: List<Reminder>,
    onMenu: () -> Unit = {},
    totalNotifications: Int,
    onLogout: () -> Unit = {},
    onSearch: () -> Unit,
    onAddLead: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    editProfile: () -> Unit,
    isProfile: Boolean = false,
    profileImage: String = "",
    onDarkTheme: () -> Unit = {}
) {
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    var expanded by remember { mutableStateOf(false) }
    var expandedCompact by remember { mutableStateOf(false) }
    var showReminders by remember {
        mutableStateOf(false)
    }

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
                Box(
                    modifier = Modifier.clickable {
                        showReminders = !showReminders
                    }
                ) {
                    Box(
                        modifier = Modifier.size(
                            35.dp
                        ).clip(RoundedCornerShape(8.dp)).clickable {
                            showReminders = !showReminders
                        }.background(
                            Color(0xFFe6f0f9),
                            shape = RoundedCornerShape(8.dp)
                        ),
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
                    if (reminders.isNotEmpty()) {
                        DropdownMenu(
                            modifier = Modifier.align(Alignment.TopEnd),
                            expanded = showReminders,
                            onDismissRequest = { showReminders = !showReminders }) {
                            reminders.forEach {
                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFe6f0f9)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            modifier = Modifier.size(
                                                50.dp
                                            ),
                                            painter = painterResource(Res.drawable.customer_ref),
                                            contentDescription = null,
                                        )
                                        Spacer(
                                            modifier = Modifier.width(8.dp)
                                        )
                                        Column {
                                            Text(
                                                text = "Tienes programada una cita",
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                            Text(
                                                text = "Haz programando una cita con ${it.customer.companyName}",
                                                color = Color.Gray,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = "Fecha: ${converteDate(it.reminderDate.toLong())}",
                                                color = Color.Gray,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                AnimatedVisibility(
                    visible = !isProfile
                ) {
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
                            if (profileImage.isNotEmpty()) {
                                AsyncImage(
                                    model = profileImage,
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(8.dp))
                                        .pointerHoverIcon(PointerIcon.Hand)
                                )
                            } else {
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                                        .align(Alignment.Center)
                                        .fillMaxSize().background(Color.White)
                                ) {
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
                                    .background(
                                        Color.Green,
                                        CircleShape
                                    )
                                    .align(Alignment.TopEnd)
                                    .offset(
                                        x = (-2).dp,
                                        y = 2.dp
                                    )
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
                                    if (profileImage.isNotBlank()) {
                                        AsyncImage(
                                            model = profileImage,
                                            contentDescription = "Image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .pointerHoverIcon(PointerIcon.Hand)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                                                .size(50.dp).background(Color.White)
                                        ) {
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
                                        imageVector = Icons.AutoMirrored.Filled.Logout,
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
                IconButton(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                    onClick = {
                        onMenu()
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = null
                        )
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Propección",
                    color = Color(0xFF007BFF),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 32.dp).weight(0.4f).pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        onClick = {
                            onSearch()
                        },
                        elevation = CardDefaults.elevatedCardElevation(15.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left side with the gradient and month
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(50.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFFFFA726),  // Naranja claro
                                                Color(0xFFFF5722)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                            // Right side with the date and schedule
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Nombre",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.Black.copy(
                                            alpha = 0.5f
                                        )
                                    )
                                )
                            }

                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(end = 16.dp).weight(0.1f).pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        onClick = {
                            onAddLead()
                        },
                        elevation = CardDefaults.elevatedCardElevation(15.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = null
                            )
                            Text(
                                text = "Agregar lead",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier.weight(0.2f)
                )
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
                            IconButton(
                                onClick = {
                                    showReminders = !showReminders
                                },
                                content = {
                                    Icon(
                                        imageVector = Icons.Outlined.Notifications,
                                        contentDescription = "Notifications",
                                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                                    )
                                }
                            )
                        }
                        if (reminders.isNotEmpty()) {
                            DropdownMenu(
                                modifier = Modifier.align(Alignment.TopEnd),
                                expanded = showReminders,
                                onDismissRequest = { showReminders = !showReminders }) {
                                reminders.forEach {
                                    Card(
                                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFFe6f0f9)
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                modifier = Modifier.size(
                                                    50.dp
                                                ),
                                                painter = painterResource(Res.drawable.customer_ref),
                                                contentDescription = null,
                                            )
                                            Spacer(
                                                modifier = Modifier.width(8.dp)
                                            )
                                            Column {
                                                Text(
                                                    text = "Tienes programada una cita",
                                                    style = MaterialTheme.typography.titleSmall,
                                                )
                                                Text(
                                                    text = "Haz programando una cita con ${it.customer.companyName}",
                                                    color = Color.Gray,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = "Fecha: ${converteDate(it.reminderDate.toLong())}",
                                                    color = Color.Gray,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                AnimatedVisibility(
                    visible = !isProfile
                ) {
                    Box(
                        modifier = Modifier.size(35.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = user.image,
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
                                .background(
                                    Color.Green,
                                    CircleShape
                                )
                                .align(Alignment.TopEnd)
                                .offset(
                                    x = (-2).dp,
                                    y = 2.dp
                                )
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
                            text = "${user.name} ${user.lastname}",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = null
                            )
                        }

                    }
                    DropdownMenu(
                        modifier = Modifier.align(Alignment.TopEnd),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
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
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                            text = { Text("Cerrar sesión") },
                            onClick = {
                                expanded = false
                                onLogout()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Logout,
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