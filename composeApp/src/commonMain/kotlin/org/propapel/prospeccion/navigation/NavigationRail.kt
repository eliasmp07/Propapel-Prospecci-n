package org.propapel.prospeccion.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.navigation.utils.NavigationItem

@Composable
fun NavigationSideBar(
    items: List<NavigationItem>, selectedItemIndex: Int, onNavigate: (Int) -> Unit,
) {
    var isTitleVisible by remember { mutableStateOf(false) }
    NavigationRail(
        header = {
            IconButton(onClick = {
                isTitleVisible = !isTitleVisible
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        },
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
        ) {
            items.forEachIndexed { index, item ->
                NavigationRailItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        onNavigate(index)
                    },
                    icon = {
                        NavigationIcon(
                            item = item, selected = selectedItemIndex == index
                        )
                    },
                    label = {
                        AnimatedVisibility(isTitleVisible, enter = scaleIn(tween(1000))) {
                            Text(
                                text = item.title,
                                fontSize = MaterialTheme.typography.body2.fontSize
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavigationIcon(
    item: NavigationItem, selected: Boolean,
) {
    BadgedBox(badge = {
        if (item.badgeCount != null) {
            Badge {
                Text(text = item.badgeCount.toString())
            }
        } else if (item.hasNews) {
            Badge()
        }
    }) {
        Icon(
            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.title
        )
    }
}