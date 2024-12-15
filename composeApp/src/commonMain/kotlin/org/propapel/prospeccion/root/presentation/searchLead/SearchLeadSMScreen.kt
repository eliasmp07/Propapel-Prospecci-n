@file:OptIn(ExperimentalAnimationApi::class)

package org.propapel.prospeccion.root.presentation.searchLead

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.root.presentation.leads.GenericContentLoading
import org.propapel.prospeccion.root.presentation.leads.components.mobile.ItemLead
import org.propapel.prospeccion.root.presentation.searchLead.components.ResultSearchScreen
import org.propapel.prospeccion.root.presentation.searchLead.components.SearchBar
import org.propapel.prospeccion.root.presentation.searchLead.components.SearchDisplay
import org.propapel.prospeccion.root.presentation.searchLead.components.StaggeredGrid
import org.propapel.prospeccion.root.presentation.searchLead.components.rememberSearchState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.empty_info

@Composable
fun SearchLeadSMScreenRoot(
    viewModel: SearchLeadSMViewModel,
    onBack: () -> Unit,
    onDetailCustomer: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    SearchLeadSMScreen(
        state = state,
        viewModel = viewModel,
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchLeadSMScreen(
    state: SearchLeadSMState,
    viewModel: SearchLeadSMViewModel,
    onAction: (SearchLeadSMAction) -> Unit
) {

    GenericContentLoading(
        modifier = Modifier.fillMaxHeight(),
        data = state.products,
        retry = {

        },
        success = {
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

                val stateSearch =
                    rememberSearchState(
                        initialResults = it,
                        suggestions = state.suggestion,
                        timeoutMillis = 600,
                    ) { query: TextFieldValue ->
                        viewModel.getCustomer(query.text)
                    }

                SearchBar(
                    query = stateSearch.query,
                    onQueryChange = { stateSearch.query = it },
                    onSearchFocusChange = { stateSearch.focused = it },
                    onClearQuery = { stateSearch.query = TextFieldValue("") },
                    onBack = { stateSearch.query = TextFieldValue("") },
                    searching = stateSearch.searching,
                    focused = stateSearch.focused,
                    onBackScreen = {
                        onAction(SearchLeadSMAction.OnBack)
                    },
                    modifier = Modifier
                )

                when (stateSearch.searchDisplay) {
                    SearchDisplay.InitialResults -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                stateSearch.initialResults,
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
                    SearchDisplay.Suggestions -> {
                        SuggestionGridLayout(
                            suggestions = stateSearch.suggestions,
                            onSuggestionClick = {
                                var text = stateSearch.query.text
                                if (text.isEmpty()) text = it else text += " $it"
                                text.trim()
                                // Set text and cursor position to end of text
                                stateSearch.query = TextFieldValue(
                                    text,
                                    TextRange(text.length)
                                )
                            },
                            onCancel = {

                            }
                        )
                    }
                    SearchDisplay.SearchInProgress -> {
                        LoadingPropapel()
                    }
                    SearchDisplay.Results -> {
                        ResultSearchScreen(
                            modifier = Modifier,
                            customers = stateSearch.searchResults,
                            onClickCustomer = {
                                onAction(SearchLeadSMAction.OnCustomerDetailClick(it))
                            }
                        )
                    }
                    SearchDisplay.NoResults -> {
                        Box(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    modifier = Modifier.size(250.dp),
                                    painter = painterResource(Res.drawable.empty_info),
                                    contentDescription = null
                                )
                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )
                                Text(
                                    "❌ Sin resultados!",
                                    fontSize = 16.sp,
                                    color = Color(0xffDD2C00)
                                )
                            }

                        }
                    }
                }


            }
        }
    )


}

data class SuggestionModel(val tag: String) {
    val id = tag.hashCode()
}

@Composable
private fun SuggestionGridLayout(
    modifier: Modifier = Modifier,
    suggestions: List<SuggestionModel>,
    onSuggestionClick: (String) -> Unit,
    onCancel: (SuggestionModel) -> Unit = {}
) {

    StaggeredGrid(
        modifier = modifier.padding(4.dp)
    ) {
        suggestions.forEach { suggestionModel ->
            CancelableChip(
                modifier = Modifier.padding(4.dp),
                suggestion = suggestionModel,
                onClick = {
                    onSuggestionClick(it.tag)
                },
            )
        }
    }
}

@Composable
fun CancelableChip(
    modifier: Modifier = Modifier,
    suggestion: SuggestionModel,
    onClick: ((SuggestionModel) -> Unit)? = null,
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick?.run {
                        invoke(suggestion)
                    }
                }
                .padding(
                    vertical = 8.dp,
                    horizontal = 10.dp
                )
        ) {
            Text(
                text = suggestion.tag,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Surface(color = Color.DarkGray, modifier = Modifier, shape = CircleShape) {
                IconButton(
                    onClick = {
                    },
                    modifier = Modifier
                        .size(16.dp)
                        .padding(1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Update,
                        tint = Color(0xFFE0E0E0),
                        contentDescription = null
                    )
                }
            }
        }
    }
}