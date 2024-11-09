package org.propapel.prospeccion.root.presentation.searchLead

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.handleResultView
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.leads.components.mobile.ItemLead
import org.propapel.prospeccion.root.presentation.searchLead.components.SearchTextField
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.empty_info
import prospeccion.composeapp.generated.resources.no_internet

@Composable
fun SearchLeadSMScreenRoot(
    viewModel: SearchLeadSMViewModel,
    onBack: () -> Unit,
    onDetailCustomer: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    SearchLeadSMScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SearchLeadSMAction.OnBack -> onBack()
                is SearchLeadSMAction.OnCustomerDetailClick -> onDetailCustomer(action.customerId)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SearchLeadSMScreen(
    state: SearchLeadSMState,
    onAction: (SearchLeadSMAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize().background(
                Brush.verticalGradient(
                    0f to PrimaryYellowLight,
                    0.6f to SoporteSaiBlue30,
                    1f to MaterialTheme.colorScheme.primary
                )
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    onAction(SearchLeadSMAction.OnBack)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = state.query,
                onValueChange = {
                    onAction(SearchLeadSMAction.OnChangeQuery(it))
                },
                shouldShowHint = false,
                onSearch = {
                    onAction(SearchLeadSMAction.OnSearch)
                }
            )
        }
        val result = handleResultView(
            isLoading = state.isSearching,
            contentLoading = {
                LoadingPropapel()
            },
            isEmpty = state.isEmpty,
            contentEmpty = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Spacer(
                        modifier = Modifier.weight(1f)
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
                        text = "Vaya no existe ningun cliente con ese nombre",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            },
            error = state.error,
            errorContent = {
                Column(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF00BCD4), // Cian claro
                                Color(0xFF009688), // Verde azulado
                                Color(0xFF00796B)  // Verde intenso
                            )
                        )
                    ).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                        painter = painterResource(Res.drawable.no_internet),
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        text = it.asString(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    ProSalesActionButtonOutline(
                        borderColor = Color.White,
                        textColor = Color.White,
                        text = "Reintentar" ,
                        onClick = {
                            onAction(SearchLeadSMAction.OnRetry)
                        }
                    )
                }
            }
        )
        if (result) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    state.products,
                    key = { it.idCustomer }) {
                    ItemLead(
                        customer = it,
                        onClick = {
                            onAction(SearchLeadSMAction.OnCustomerDetailClick(it))
                        }
                    )
                }
            }
        }

    }
}