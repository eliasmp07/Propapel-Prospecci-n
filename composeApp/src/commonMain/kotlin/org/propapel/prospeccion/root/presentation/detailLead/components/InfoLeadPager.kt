package org.propapel.prospeccion.root.presentation.detailLead.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.TypeSpecimen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Interaction
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState
import org.propapel.prospeccion.root.presentation.detailReminderCustomer.dayOfWeekSpanish
import org.propapel.prospeccion.root.presentation.leads.components.ActionIcon
import org.propapel.prospeccion.root.presentation.leads.components.SwipeableItemCalendarWithActions
import org.propapel.prospeccion.root.presentation.leads.components.mobile.isScrolled
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.appointment_customer
import prospeccion.composeapp.generated.resources.email_outline
import prospeccion.composeapp.generated.resources.empty_info
import prospeccion.composeapp.generated.resources.ic_calendar
import prospeccion.composeapp.generated.resources.ic_calendar_outline
import prospeccion.composeapp.generated.resources.ic_info
import prospeccion.composeapp.generated.resources.ic_info_outline
import prospeccion.composeapp.generated.resources.ic_product
import prospeccion.composeapp.generated.resources.ic_product_otline
import prospeccion.composeapp.generated.resources.ic_project
import prospeccion.composeapp.generated.resources.ic_project_outline
import prospeccion.composeapp.generated.resources.ic_reminder
import prospeccion.composeapp.generated.resources.ic_reminder_outline
import prospeccion.composeapp.generated.resources.interactions_customer
import prospeccion.composeapp.generated.resources.leads_info

enum class NotificationPager(
    val titleResId: StringResource,
    val drawableRes: DrawableResource,
    val outlineDrawableRes: DrawableResource
) {

    INFO_LEAD(
        Res.string.leads_info,
        Res.drawable.ic_info,
        Res.drawable.ic_info_outline
    ),
    INTERACTIONS_CUSTOMER(
        Res.string.interactions_customer,
        Res.drawable.ic_calendar,
        Res.drawable.ic_calendar_outline
    ),
    APPOINTMENT_CUSTOMER(
        Res.string.appointment_customer,
        Res.drawable.ic_reminder_outline,
        Res.drawable.ic_reminder
    ),
    PRODUCTS_INTERERED(
        Res.string.appointment_customer,
        Res.drawable.ic_product,
        Res.drawable.ic_product_otline

    ),
    PROJECT_CUSTOMER(
        Res.string.appointment_customer,
        Res.drawable.ic_project,
        Res.drawable.ic_project_outline
    )
}

@Preview
@Composable
fun InfoLeadPagerScreen(
    state: DetailLeadSMState,
    pagerState: PagerState,
    onCloseAppointment: (Reminder) -> Unit,
    projects: List<Project>,
    customer: Customer,
    onAction: (DetailLeadAction) -> Unit,
    onDeleteProject: (Project) -> Unit,
    pages: Array<NotificationPager>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.background(Color.White).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp), // Remove spacing between children
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val corrutineScope = rememberCoroutineScope()
        TabRow(
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        color = Color(0xFFEB442C),
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    )
                }
            },
            selectedTabIndex = pagerState.currentPage //tabIndex.value!!
        ) {
            pages.forEachIndexed { index, notificationPager ->
                val title = stringResource(notificationPager.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        corrutineScope.launch { pagerState.animateScrollToPage(index) }
                    },
                    icon = {
                        Icon(
                            painter = if (pagerState.currentPage == index) painterResource(notificationPager.drawableRes) else painterResource(notificationPager.outlineDrawableRes),
                            contentDescription = title,
                            tint = Color.Black
                        )
                    },
                    unselectedContentColor = Color.White,
                    selectedContentColor = Color.White,
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            when (pages[index]) {
                NotificationPager.INFO_LEAD -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Business,
                                contentDescription = null
                            )
                            Text(
                                text = "Nombre de la empresa:",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = customer.companyName,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Person,
                                contentDescription = null
                            )
                            Text(
                                text = "Contacto:",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = customer.contactName,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Phone,
                                contentDescription = null
                            )
                            Text(
                                text = "Numero de contacto:",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = customer.phoneNumber,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.TypeSpecimen,
                                contentDescription = null
                            )
                            Text(
                                text = "Tipo de cliente",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = customer.typeClient,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.email_outline),
                                contentDescription = null
                            )
                            Text(
                                text = "Correo electronico",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = customer.email,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.LocationCity,
                                contentDescription = null
                            )
                            Text(
                                text = "Dirección",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = customer.address ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        Spacer(
                            modifier = Modifier.weight(1f),
                        )
                        ProSalesActionButtonOutline(
                            text = "Editar información",
                            onClick = {
                                onAction(DetailLeadAction.OnUpdateCustomerClick(customer.idCustomer.toString()))
                            }
                        )
                    }
                }
                NotificationPager.INTERACTIONS_CUSTOMER -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val lazyColumState = rememberLazyListState()
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            state = lazyColumState,
                        ) {
                            if (customer.interactions.isNotEmpty()) {
                                items(customer.interactions) {
                                    ItemInterationCustomer(it)
                                }
                                items(
                                    state.reminders,
                                    key = { it.reminderId }) {
                                    if (it.isCompleted == true) {
                                        ItemReminders(
                                            interaction = it,
                                            modifier = Modifier.animateItem()
                                        )
                                    }

                                }
                            } else {
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxSize().padding(16.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(
                                            modifier = Modifier.height(32.dp)
                                        )
                                        Image(
                                            modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                                            painter = painterResource(Res.drawable.empty_info),
                                            contentDescription = null
                                        )
                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )
                                        Text(
                                            text = "No haz tenido ninguna entrevista con el cliente",
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                            }
                        }

                        ExtendedFloatingActionButton(
                            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.CalendarToday,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            shape = RoundedCornerShape(30.dp),
                            expanded = lazyColumState.isScrolled(),
                            onClick = {
                                onAction(
                                    DetailLeadAction.AddInteractionsClick(
                                        idCustomer = customer.idCustomer.toString(),
                                        reminderId = "0",
                                        date = 0
                                    )
                                )
                            },
                            text = {
                                Text(
                                    text = "Capturar actividad",
                                    color = Color.White
                                )
                            }
                        )
                    }
                }
                NotificationPager.APPOINTMENT_CUSTOMER -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val lazyColumState = rememberLazyListState()
                        LazyColumn(
                            state = lazyColumState,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize().padding(16.dp)
                        ) {
                            val reminders = state.reminders.filter {
                                it.isCompleted == false
                            }
                            if (reminders.isNotEmpty()) {
                                items(
                                    reminders,
                                    key = {
                                        it.reminderId
                                    }) {
                                    var expanded by remember {
                                        mutableStateOf(false)
                                    }
                                    SwipeableItemCalendarWithActions(
                                        isRevealed = false,
                                        onExpanded = {
                                            expanded = true
                                        },
                                        onCollapsed = {
                                            expanded = false
                                        },
                                        actions = {
                                            ActionIcon(
                                                onClick = {
                                                    onAction(DetailLeadAction.OnCancelReminderClick(it))
                                                },
                                                backgroundColor = Color.Red,
                                                icon = Icons.Default.Delete,
                                                tint = Color.White,
                                                modifier = Modifier.fillMaxHeight()
                                            )
                                            ActionIcon(
                                                onClick = {
                                                    onAction(DetailLeadAction.OnUpdateReminder(it))
                                                },
                                                backgroundColor = MaterialTheme.colorScheme.primary,
                                                icon = Icons.Default.Update,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                            )
                                            ActionIcon(
                                                modifier = Modifier.fillMaxHeight(),
                                                onClick = {
                                                    onAction(
                                                        DetailLeadAction.AddInteractionsClick(
                                                            idCustomer = customer.idCustomer.toString(),
                                                            reminderId = it.reminderId.toString(),
                                                            date = it.reminderDate.toLong()
                                                        )
                                                    )
                                                },
                                                backgroundColor = SuccessGreen,
                                                icon = Icons.Default.CheckCircle,
                                            )
                                        },
                                    ) {
                                        ItemCustomerReminder(
                                            reminder = it,
                                            isExpanded = expanded,
                                            customer = customer,
                                            onDetailReminder = {
                                                onAction(DetailLeadAction.OnDetailReminderCustomer(it))
                                            }
                                        )
                                    }
                                }
                            } else {
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                                    ) {
                                        Spacer(
                                            modifier = Modifier.height(32.dp)
                                        )
                                        Image(
                                            modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                                            painter = painterResource(Res.drawable.empty_info),
                                            contentDescription = null
                                        )
                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )
                                        Text(
                                            text = "No tienes ninguna cita con el cliente, crea una nueva para poder visualizarlo",
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                        ExtendedFloatingActionButton(
                            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.NotificationAdd,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            shape = RoundedCornerShape(30.dp),
                            expanded = lazyColumState.isScrolled(),
                            onClick = {
                                onAction(DetailLeadAction.OnToggleCreateAppointmentDialog)
                            },
                            text = {
                                Text(
                                    text = "Crear cita",
                                    color = Color.White
                                )
                            }
                        )
                    }
                }
                NotificationPager.PRODUCTS_INTERERED -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val lazyColumState = rememberLazyListState()
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            state = lazyColumState,
                        ) {
                            val filterPurchase = customer.purchase.filter {
                                !it.isIntoProduct
                            }
                            if (filterPurchase.isNotEmpty()) {
                                items(
                                    customer.purchase,
                                    key = {
                                        it.purcheseId
                                    }) {
                                    ItemProductInteresed(it)
                                }
                            } else {
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxSize().padding(16.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(
                                            modifier = Modifier.height(32.dp)
                                        )
                                        Image(
                                            modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                                            painter = painterResource(Res.drawable.empty_info),
                                            contentDescription = null
                                        )
                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )
                                        Text(
                                            text = "El cliente no se ha interesado con ningun producto",
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                            }
                        }
                        ExtendedFloatingActionButton(
                            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            icon = {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.ic_product_otline),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            shape = RoundedCornerShape(30.dp),
                            expanded = lazyColumState.isScrolled(),
                            onClick = {
                                onAction(DetailLeadAction.OnCreateProject(customer.idCustomer.toString()))
                            },
                            text = {
                                Text(
                                    text = "Crear proyecto",
                                    color = Color.White
                                )
                            }
                        )
                    }
                }
                NotificationPager.PROJECT_CUSTOMER -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val lazyColumState = rememberLazyListState()
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            state = lazyColumState,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (projects.isNotEmpty()) {
                                items(
                                    projects,
                                    key = { it.id }) {
                                    ItemProjectCustomer(
                                        it,
                                        onDelete = {
                                            onDeleteProject(it)
                                        })
                                }
                            } else {
                                item {
                                    Column(
                                        modifier = Modifier.fillMaxSize().padding(16.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(
                                            modifier = Modifier.height(32.dp)
                                        )
                                        Image(
                                            modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                                            painter = painterResource(Res.drawable.empty_info),
                                            contentDescription = null
                                        )
                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )
                                        Text(
                                            text = "No tienes ningun proyecto con el cliente",
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun ItemReminders(
    modifier: Modifier = Modifier,
    interaction: Reminder
) {
    val date = Instant.fromEpochMilliseconds(interaction.reminderDate.toLong()).toLocalDateTime(TimeZone.UTC)
    Box(
        modifier = Modifier
            .fillMaxWidth().padding(16.dp)
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp),
            elevation = CardDefaults.elevatedCardElevation(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Left side with the gradient and month
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFF6363),
                                    Color(0xFFAB47BC)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = monthGet(date.monthNumber).take(3),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .rotate(-90f)
                    )
                }
                // Right side with the date and schedule
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date.date.dayOfWeekSpanish(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                    Text(
                        text = date.dayOfMonth.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tipo de interacion: ${interaction.typeAppointment}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ItemInterationCustomer(
    interaction: Interaction
) {
    val date = Instant.fromEpochMilliseconds(interaction.interactionDate).toLocalDateTime(TimeZone.UTC)
    Box(
        modifier = Modifier
            .fillMaxWidth().padding(16.dp)
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            elevation = CardDefaults.elevatedCardElevation(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Left side with the gradient and month
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFF6363),
                                    Color(0xFFAB47BC)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = monthGet(date.monthNumber).take(3),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .rotate(-90f)
                    )
                }
                // Right side with the date and schedule
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date.date.dayOfWeekSpanish(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                    Text(
                        text = date.dayOfMonth.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tipo de interacion: ${interaction.interactionType}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonItemDirectAccess(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.size(100.dp).shadow(
            5.dp,
            RoundedCornerShape(20.dp)
        ).background(
            brush = Brush.verticalGradient(
                colors = colors
            ),
            shape = RoundedCornerShape(20.dp)
        ).clickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

    }
}