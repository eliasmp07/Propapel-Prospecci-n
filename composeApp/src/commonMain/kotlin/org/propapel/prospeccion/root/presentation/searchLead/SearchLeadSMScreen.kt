package org.propapel.prospeccion.root.presentation.searchLead

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.handleResultView
import org.propapel.prospeccion.root.presentation.leads.components.mobile.ItemLead
import org.propapel.prospeccion.root.presentation.searchLead.components.SearchTextField

@Composable
fun SearchLeadSMScreenRoot(
    viewModel: SearchLeadSMViewModel,
    onDetailCustomer: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    SearchLeadSMScreen(
        state = state,
        onAction = { action ->
            when (action) {
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
            .padding(16.dp)
    ) {

        SearchTextField(
            text = state.query,
            onValueChange = {
                onAction(SearchLeadSMAction.OnChangeQuery(it))
            },
            shouldShowHint = false,
            onSearch = {
                onAction(SearchLeadSMAction.OnSearch)
            }
        )

        val result = handleResultView(
            isLoading = state.isSearching,
            contentLoading = {
                LoadingPropapel()
            },
            isEmpty = state.isEmpty,
            contentEmpty = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "no hay",
                        color = Color.Black
                    )
                }
            },
            error = state.error,
            errorContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "error",
                        color = Color.Black
                    )
                }
            }
        )
        if (result) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
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