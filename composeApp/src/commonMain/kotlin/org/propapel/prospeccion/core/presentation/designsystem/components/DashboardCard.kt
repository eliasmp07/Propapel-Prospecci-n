@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class
)

package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.propapel.prospeccion.root.domain.models.Customer

@Composable
fun DashboardCard(
    title: String,
    value: String,
    percentage: String,
    customers: List<Customer> = listOf(),
    modifier: Modifier = Modifier,
    icon: ImageVector,
    background: Color,
) {
    var showMoreInfo by remember {
        mutableStateOf(false)
    }
    val windowClass = calculateWindowSizeClass()
    val showNavigationRail = windowClass.widthSizeClass != WindowWidthSizeClass.Compact

    if (showNavigationRail) {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth().animateContentSize(),
        ) {
            Column(
                modifier = Modifier.animateContentSize()
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = value,
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                onClick = {
                                    showMoreInfo = !showMoreInfo
                                },
                                content = {
                                    Icon(
                                        imageVector = if(showMoreInfo) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                                        contentDescription = null,
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = title,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            Text(
                                text = percentage,
                                fontSize = 14.sp,
                                color = background
                            )
                            Icon(
                                imageVector = Icons.Outlined.StackedLineChart,
                                contentDescription = null,
                                tint = background
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = background,
                                shape = CircleShape
                            )
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (showMoreInfo) {
                    if (customers.isNotEmpty()) {
                        customers.forEach {
                            Text(
                                text = it.companyName,
                                fontSize = 14.sp,
                                color = Color.Black,
                            )
                        }
                    }
                }
            }
        }
    } else {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.End)
                            .background(
                                color = background,
                                shape = CircleShape
                            )
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = value,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = title,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(
                            text = percentage,
                            fontSize = 14.sp,
                            color = background
                        )
                        Icon(
                            imageVector = Icons.Outlined.StackedLineChart,
                            contentDescription = null,
                            tint = background
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}