package org.propapel.prospeccion.root.presentation.detailLead.components.desktop

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.TypeSpecimen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState
import org.propapel.prospeccion.root.presentation.detailLead.components.ItemCustomerReminder
import org.propapel.prospeccion.root.presentation.detailLead.components.ItemInterationCustomer
import org.propapel.prospeccion.root.presentation.detailLead.components.ItemProductInteresed
import org.propapel.prospeccion.root.presentation.detailLead.components.ItemProjectCustomer
import org.propapel.prospeccion.root.presentation.detailLead.components.ItemReminders
import org.propapel.prospeccion.root.presentation.detailLead.components.NotificationPager
import org.propapel.prospeccion.root.presentation.leads.components.ActionIcon
import org.propapel.prospeccion.root.presentation.leads.components.SwipeableItemCalendarWithActions
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.email_outline
import prospeccion.composeapp.generated.resources.empty_info
import prospeccion.composeapp.generated.resources.ic_calendar_outline
import prospeccion.composeapp.generated.resources.ic_product_otline
import prospeccion.composeapp.generated.resources.ic_reminder
import prospeccion.composeapp.generated.resources.projects_ic_dra

@Composable
actual fun DetailLeadDesktop(
    state: DetailLeadSMState,
    pagerState: PagerState,
    onCloseAppointment: (Reminder) -> Unit,
    projects: List<Project>,
    customer: Customer,
    onAction: (DetailLeadAction) -> Unit,
    onDeleteProject: (Project) -> Unit,
    pages: Array<NotificationPager>,
    modifier: Modifier
) {

    val lazyGridState = rememberLazyGridState()
    val lazyGridAppointment = rememberLazyGridState()

    Row(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryYellowLight,
                0.6f to SoporteSaiBlue30,
                1f to MaterialTheme.colorScheme.primary
            )
        ).padding(30.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.weight(0.5f),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.elevatedCardElevation(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Informacion del cliente",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier.padding(12.dp).size(200.dp).clip(CircleShape).border(BorderStroke(3.dp, Color.Black), CircleShape)
                        ){
                        KottieAnimationUtil(
                            modifier = Modifier.fillMaxSize(),
                            interations = 1,
                            fileRoute = "files/anim_customer_person.json"
                        )}
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
                }

            }
            ElevatedCard(
                modifier = Modifier.weight(0.5f),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.elevatedCardElevation(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.projects_ic_dra),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Proyectos",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                onAction(DetailLeadAction.OnCreateProject(customer.idCustomer.toString()))
                            },
                            content = {
                                Text(
                                    text = "Crear"
                                )
                            }
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyVerticalGrid(
                            state = lazyGridState,
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp).padding(horizontal = 16.dp).padding(end = 12.dp)
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
                                item(
                                    span = {
                                        GridItemSpan(maxLineSpan)
                                    }
                                ) {
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
                        VerticalScrollbar(
                            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                                .pointerHoverIcon(PointerIcon.Hand),
                            adapter = rememberScrollbarAdapter(
                                scrollState = lazyGridState
                            )
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.weight(0.5f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                ElevatedCard(
                    modifier = Modifier.weight(0.5f),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.ic_calendar_outline),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Actividades",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            OutlinedButton(
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                shape = RoundedCornerShape(12.dp),
                                onClick = {
                                    onAction(
                                        DetailLeadAction.AddInteractionsClick(
                                            idCustomer = customer.idCustomer.toString(),
                                            reminderId = "0",
                                            date = 0
                                        )
                                    )
                                },
                                content = {
                                    Text(
                                        text = "Crear"
                                    )
                                }
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val lazyColumState = rememberLazyListState()
                            LazyColumn (
                                state = lazyColumState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp).padding(horizontal = 16.dp).padding(end = 12.dp)
                            ) {
                                if (customer.interactions.isNotEmpty()) {
                                    items(
                                        customer.interactions,
                                        key = { it.interactionId }) {
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
                                }else {
                                    item() {
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
                                                text = "No tienes ninguna cita con el cliente, crea una nueva para poder visualizarlo",
                                                style = MaterialTheme.typography.titleMedium,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                }
                            }
                            VerticalScrollbar(
                                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                                    .pointerHoverIcon(PointerIcon.Hand),
                                adapter = rememberScrollbarAdapter(
                                    scrollState = lazyColumState
                                )
                            )
                        }
                    }
                }
                ElevatedCard(
                    modifier = Modifier.weight(0.5f),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.ic_product_otline),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Productos de interes",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val lazyColumProductInterestState = rememberLazyListState()
                            LazyColumn (
                                state = lazyColumProductInterestState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp).padding(horizontal = 16.dp).padding(end = 12.dp)
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
                                    item(

                                    ) {
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
                            VerticalScrollbar(
                                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                                    .pointerHoverIcon(PointerIcon.Hand),
                                adapter = rememberScrollbarAdapter(
                                    scrollState = lazyColumProductInterestState
                                )
                            )
                        }
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier.weight(0.5f),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.elevatedCardElevation(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_reminder),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Citas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                onAction(DetailLeadAction.OnToggleCreateAppointmentDialog)
                            },
                            content = {
                                Text(
                                    text = "Crear"
                                )
                            }
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyVerticalGrid(
                            state = lazyGridAppointment,
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp).padding(horizontal = 16.dp).padding(end = 12.dp),
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
                                        modifier = Modifier.padding(8.dp),
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
                            }else {
                                item(
                                    span = {
                                        GridItemSpan(maxLineSpan)
                                    }
                                ) {
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
                        VerticalScrollbar(
                            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                                .pointerHoverIcon(PointerIcon.Hand),
                            adapter = rememberScrollbarAdapter(
                                scrollState = lazyGridAppointment
                            )
                        )
                    }
                }
            }

        }
    }
}