package org.propapel.prospeccion.root.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.util.shimmerEffect

@Composable
fun ShimmerEffectDashboard(
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    0f to PrimaryYellowLight,
                    0.6f to SoporteSaiBlue30,
                    1f to MaterialTheme.colorScheme.primary
                )
            )
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier.height(25.dp).width(230.dp).shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.height(18.dp).width(150.dp).shimmerEffect()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Column {
                Box(
                    modifier = Modifier
                        .aspectRatio(16f / 7f)
                        .clip(RoundedCornerShape(20.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            Column {
                Box(
                    modifier = Modifier
                        .aspectRatio(16f / 10f)
                        .padding(vertical = 8.dp).clip(RoundedCornerShape(12.dp)).shimmerEffect()
                )
            }
        }
        item {
            Column {
                Box(
                    modifier = Modifier.aspectRatio(16f / 7f)
                        .padding(vertical = 8.dp).clip(RoundedCornerShape(12.dp)).shimmerEffect()
                )
            }
        }
        item {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp).clip(RoundedCornerShape(12.dp)).shimmerEffect()
                )
            }
        }
        item {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp).clip(RoundedCornerShape(12.dp)).shimmerEffect()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}