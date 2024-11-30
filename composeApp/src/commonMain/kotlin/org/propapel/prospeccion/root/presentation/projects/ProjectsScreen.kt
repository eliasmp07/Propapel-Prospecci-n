package org.propapel.prospeccion.root.presentation.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.components.Direction
import org.propapel.prospeccion.core.presentation.designsystem.components.rememberSwipeableCardState
import org.propapel.prospeccion.core.presentation.designsystem.components.swipableCard
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.presentation.detailLead.components.CustomCircularProgressIndicator
import org.propapel.prospeccion.root.presentation.detailLead.components.progressColor
import org.propapel.prospeccion.root.presentation.users.CircleButton

/**
 * Pantalla en modo de prueba
 *
 */
@Composable
fun ProjectsScreenRoot(){
   // ProjectsScreen()
}

@Composable
fun ProjectsScreen(
    state: ProjectsScreenState
){
    Box {
        val states = state.projects.reversed()
            .map { it to rememberSwipeableCardState() }
        var hint by remember {
            mutableStateOf("Swipe a card or press a button below")
        }

        val scope = rememberCoroutineScope()
        Box(
            Modifier
                .padding(24.dp)
                .fillMaxSize()
                .align(Alignment.Center)) {
            states.forEach { (matchProfile, state) ->
                if (state.swipedDirection == null) {
                    ProjectCard(
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
                        project = matchProfile
                    )
                }
                LaunchedEffect(matchProfile, state.swipedDirection) {
                    if (state.swipedDirection != null) {
                        hint = "You swiped "
                    }
                }
            }
        }
        Row(
            Modifier
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
        }
    }
}

@Composable
private fun ProjectCard(
    modifier: Modifier,
    project: Project
) {
    Card(modifier) {
        Box {
            CustomCircularProgressIndicator(
                initialValue = project.progress,
                circleRadius = 200f,
                primaryColor = progressColor(project.progress, project.status),
                onPositionChange = {

                },
                modifier = Modifier.size(200.dp),
                secondaryColor = Color.Black
            )
//Scrim(Modifier.align(Alignment.BottomCenter))
            Column(Modifier.align(Alignment.BottomStart)) {
                Text(text = project.nameProject,
                     color = Color.Black,
                     fontSize = 22.sp,
                     fontWeight = FontWeight.Medium,
                     modifier = Modifier.padding(10.dp))
            }
        }
    }
}