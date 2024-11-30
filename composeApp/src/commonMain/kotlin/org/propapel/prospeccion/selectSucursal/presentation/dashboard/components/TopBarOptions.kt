package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
import org.propapel.prospeccion.root.presentation.detailLead.components.ButtonItemDirectAccess
import org.propapel.prospeccion.selectSucursal.presentation.dashboard.getImageSucursal
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.logo
import prospeccion.composeapp.generated.resources.mid_reference

@Composable
fun TopBarOptions(
    user: AuthInfo
){
    Box(
        modifier = Modifier.height(220.dp).fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(170.dp).shadow(
                2.dp,
                RoundedCornerShape(
                    bottomEnd = 30.dp,
                    bottomStart = 30.dp
                )
            ).background(
                color = PrimaryYellowLight,
                shape = RoundedCornerShape(
                    bottomEnd = 30.dp,
                    bottomStart = 30.dp
                )
            )
        ) {
            Image(
                modifier = Modifier.fillMaxWidth().height(170.dp),
                painter = painterResource(getImageSucursal(user) ?: Res.drawable.mid_reference),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PrimaryYellowLight,
                                Color.Black.copy(alpha = 0.3f)
                            ),
                            startY = 0f,
                            endY = 100f
                        ),
                        shape = RoundedCornerShape(
                            bottomEnd = 30.dp,
                            bottomStart = 30.dp
                        )
                    )
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                Column {
                    Text(
                        text = "Bienvenido",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${user.name} ${user.lastname}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = if (user.sucursales.isEmpty()) "" else user.sucursales.first(),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                if (user.image.isNotBlank()) {
                    AsyncImage(
                        model = user.image,
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(
                                90.dp
                            )
                            .clip(CircleShape)
                            .pointerHoverIcon(PointerIcon.Hand)
                            .border(
                                border = BorderStroke(
                                    2.dp,
                                    Color.White
                                ),
                                shape = CircleShape
                            )
                    )
                } else {
                    Box(
                        modifier = Modifier.size(90.dp).clip(CircleShape).background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(
                                    70.dp
                                )
                                .clip(CircleShape)
                                .pointerHoverIcon(PointerIcon.Hand)
                                .border(
                                    border = BorderStroke(
                                        2.dp,
                                        Color.White
                                    ),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth().align(Alignment.BottomCenter).padding(horizontal = 32.dp).clickable {

                }
        ) {
            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                elevation = CardDefaults.elevatedCardElevation(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonItemDirectAccess(
                        modifier = Modifier.padding(8.dp).weight(1f),
                        text = "Crear Lead",
                        icon = Icons.Filled.Person,
                        colors = listOf(
                            Color(0xFFFF6363),
                            Color(0xFFAB47BC)
                        ),
                        onClick = {
                           // onAction(DashboardSMAction.OnMoveLeadScreenClick)
                        }
                    )
                    ButtonItemDirectAccess(
                        modifier = Modifier.padding(8.dp).weight(1f),
                        text = "Crear cita",
                        icon = Icons.Filled.CalendarMonth,
                        colors = listOf(
                            Color(0xFFFF6363),
                            Color(0xFFAB47BC)
                        ),
                        onClick = {
                            //onAction(DashboardSMAction.OnCreateReminderClick)
                        }
                    )
                    ButtonItemDirectAccess(
                        modifier = Modifier.padding(8.dp).weight(1f),
                        text = "Buscar",
                        icon = Icons.Filled.Search,
                        colors = listOf(
                            Color(0xFFFF6363),
                            Color(0xFFAB47BC)
                        ),
                        onClick = {
                           // onAction(DashboardSMAction.OnSearchLead)
                        }
                    )
                }
            }
        }

    }
    Spacer(modifier = Modifier.height(16.dp))
}