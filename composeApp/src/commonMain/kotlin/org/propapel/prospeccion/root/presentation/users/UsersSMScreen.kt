package org.propapel.prospeccion.root.presentation.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.components.Direction
import org.propapel.prospeccion.core.presentation.designsystem.components.rememberSwipeableCardState
import org.propapel.prospeccion.core.presentation.designsystem.components.swipableCard
import org.propapel.prospeccion.selectSucursal.domain.model.UserItem

@Composable
fun UsersSMScreenRoot(
    viewModel: UserSMViewModel,
    sucusalId: Int,
    onAddUser: () -> Unit
) {
    LaunchedEffect(Unit){
        viewModel.getUserBySucursal(sucusalId)
    }
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
    Box {
        val states = state.users.reversed()
            .map { it to rememberSwipeableCardState() }
        var hint by remember {
            mutableStateOf("Swipe a card or press a button below")
        }

        val scope = rememberCoroutineScope()
        Box(Modifier
                .padding(24.dp)
                .fillMaxSize()
                .align(Alignment.Center)) {
            states.forEach { (matchProfile, state) ->
                if (state.swipedDirection == null) {
                    ProfileCard(
                        modifier = Modifier
                            .fillMaxSize()
                            .swipableCard(
                                state = state,
                                blockedDirections = listOf(Direction.Down),
                                onSwiped = {
                                    // swipes are handled by the LaunchedEffect
                                    // so that we track button clicks & swipes
                                    // from the same place
                                },
                                onSwipeCancel = {
                                    hint = "You canceled the swipe"
                                }
                            ),
                        user = matchProfile
                    )
                }
                LaunchedEffect(matchProfile, state.swipedDirection) {
                    if (state.swipedDirection != null) {
                        hint = "You swiped "
                    }
                }
            }
        }
        Row(Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CircleButton(
                onClick = {
                    scope.launch {
                        val last = states.reversed()
                            .firstOrNull {
                                it.second.offset.value == Offset(0f, 0f)
                            }?.second
                        last?.swipe(Direction.Left)
                    }
                },
                icon = Icons.Rounded.Close
            )
            CircleButton(
                onClick = {
                    scope.launch {
                        val last = states.reversed()
                            .firstOrNull {
                                it.second.offset.value == Offset(0f, 0f)
                            }?.second

                        last?.swipe(Direction.Right)
                    }
                },
                icon = Icons.Rounded.Favorite
            )
        }
    }
}

@Composable
fun CircleButton(
    onClick: () -> Unit,
    icon: ImageVector,
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .size(56.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
        onClick = onClick
    ) {
        Icon(icon, null,
             tint = Color.White)
    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier,
    user: UserItem,
) {
    Card(modifier) {
        Column {
            if (user.image.isEmpty()){
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        .size(40.dp).background(Color.White)
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
            }else{
                AsyncImage(
                    model = user.image,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .pointerHoverIcon(PointerIcon.Hand)
                )
            }
            Column(
                modifier = Modifier.clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            ) {
                Text(text = user.name,
                     color = Color.Black,
                     fontSize = 22.sp,
                     fontWeight = FontWeight.Medium,
                     modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
private fun Hint(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(modifier
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            .height(180.dp)
            .fillMaxWidth())
}
