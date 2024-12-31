package org.propapel.prospeccion.navigation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.navigation.utils.NavigationItem

@Composable
fun SidebarMenu(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    user: AuthInfo,
    onMenuItemClick: (Int) -> Unit,
    initialExpandedState: Boolean = true,
) {
    var isExpanded by remember { mutableStateOf(initialExpandedState) }
    val expandIcon = if (isExpanded) Icons.Default.ArrowBackIosNew else Icons.Default.Menu
    val sidebarWidth by animateDpAsState(targetValue = if (isExpanded) 240.dp else 75.dp)

    Column(
        modifier = Modifier
            .width(sidebarWidth)
            .fillMaxHeight()
            .background(Color(0xFFf1f5f9))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()).animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .animateContentSize(
                    tween(
                        durationMillis = 100,
                        delayMillis = 400,
                        easing = EaseInBounce
                    )
                )
                .align(Alignment.End)
        ) {
            Icon(expandIcon, contentDescription = "Expand/Collapse Sidebar")
        }
        Box(
            modifier = Modifier.padding(
                horizontal = if (isExpanded) 16.dp else 4.dp,
                vertical = if (isExpanded) 8.dp else 4.dp
            ).align(Alignment.CenterHorizontally).size( if (isExpanded) 100.dp else 30.dp).clip(CircleShape)
        ) {
            if (
                user.image.isEmpty()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            BorderStroke(
                                2.dp,
                                androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            ),
                            CircleShape
                        ).background(Color.White)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.background),
                        contentScale = ContentScale.Crop,
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }
            } else {
                AsyncImage(
                    modifier = Modifier.fillMaxSize().border(
                        BorderStroke(
                            2.dp,
                            androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        ),
                        CircleShape
                    ),
                    model = user.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column {
            items.forEachIndexed { index, item ->
                MenuItem(
                    name = item.title,
                    icon = item.selectedIcon,
                    selected = selectedItemIndex == index,
                    onMenuItemClick = { onMenuItemClick(index) },
                    expanded = isExpanded
                )
            }
        }
    }
}

@Composable
fun MenuItem(
    name: String,
    icon: ImageVector,
    selected: Boolean,
    onMenuItemClick: () -> Unit,
    expanded: Boolean,
) {
    val backgroundColor = if (selected) Color(0xFF007BFF) else Color.Transparent
    val contentColor = if (selected) Color.White else Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onMenuItemClick)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(
                horizontal = if (expanded) 16.dp else 4.dp,
                vertical = if (expanded) 8.dp else 4.dp
            )
            .height(48.dp).pointerHoverIcon(PointerIcon.Hand),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = name, tint = contentColor)
        if (expanded) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = name, color = contentColor, style = MaterialTheme.typography.body1)
        }
    }
}