package org.propapel.prospeccion.root.presentation.detailLead.components.desktop

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.TypeSpecimen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.util.animateEnterBottom
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState
import org.propapel.prospeccion.root.presentation.detailLead.components.ItemProjectCustomer
import org.propapel.prospeccion.root.presentation.detailLead.components.NotificationPager
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person
import prospeccion.composeapp.generated.resources.email_outline
import prospeccion.composeapp.generated.resources.empty_info

/**
 * Implementacion de escritorio para el verticalscroll
 */
@Composable
expect fun DetailLeadDesktop(
    state: DetailLeadSMState,
    pagerState: PagerState,
    onCloseAppointment: (Reminder) -> Unit,
    projects: List<Project>,
    customer: Customer,
    onAction: (DetailLeadAction) -> Unit,
    onDeleteProject: (Project) -> Unit,
    pages: Array<NotificationPager>,
    modifier: Modifier = Modifier,
)