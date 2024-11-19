@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState

@Composable
fun ModalBottomSheetDeleteProject(
    state: DetailLeadSMState,
    sheetState: SheetState,
    onAction: (DetailLeadAction)-> Unit,
    onDeleteProject: () -> Unit,
    scope: CoroutineScope = rememberCoroutineScope(),
    onDismissRequest: () -> Unit
) {
   ModalBottomSheet(
        onDismissRequest = {
           onDismissRequest()
        },
        dragHandle = {
           Row(
               modifier = Modifier.fillMaxWidth()
           ){
               Spacer(
                   modifier = Modifier.weight(1f)
               )
               TextButton(
                   onClick = {
                       scope.launch { sheetState.hide() }.invokeOnCompletion {
                           if (!sheetState.isVisible) {
                               onDismissRequest()
                           }
                       }
                   },
                   content = {
                       Text(
                           text = "Cancelar",
                           color = MaterialTheme.colorScheme.error,
                           style = MaterialTheme.typography.titleMedium
                       )
                   }
               )

           }
        },
        sheetState = sheetState
    ){
       HorizontalDivider()
       Column(
           modifier = Modifier.padding(16.dp)
       ) {

           Text(
               modifier = Modifier.align(Alignment.CenterHorizontally),
               text = "Eliminar proyecto",
               style = MaterialTheme.typography.headlineMedium,
               color = Color.Black,
               fontWeight = FontWeight.Bold
           )
           Spacer(Modifier.height(16.dp))
           Text(
               modifier = Modifier.align(Alignment.CenterHorizontally),
               text = "Motivos de eliminacion de proyecto",
               textAlign = TextAlign.Center,
               fontWeight = FontWeight.Bold,
               color = Color.Black,
               style = MaterialTheme.typography.headlineSmall,
           )
           Spacer(
               modifier = Modifier.height(8.dp)
           )
           ProSalesTextField(
               title = "Motivos",
               state = state.motivos,
               onTextChange = {
                    onAction(DetailLeadAction.OnMotivosChange(it))
               },
               keyboardOptions = KeyboardOptions(
                   keyboardType = KeyboardType.Text,
                   imeAction = ImeAction.Done
               ),
               keyboardActions = KeyboardActions(
                   onNext = {

                   }
               ),
               colors = Color.Black,
               startIcon = Icons.Filled.Notes,
               maxLines = 104
           )
           Spacer(Modifier.height(8.dp))
           ProSalesTextField(
               title = "Competencia",
               state = state.competencia,
               onTextChange = {
                    onAction(DetailLeadAction.OnCompetenciaChange(it))
               },
               keyboardOptions = KeyboardOptions(
                   keyboardType = KeyboardType.Text,
                   imeAction = ImeAction.Done
               ),
               keyboardActions = KeyboardActions(
                   onNext = {

                   }
               ),
               colors = Color.Black,
               startIcon = Icons.Filled.Business,
               maxLines = 104
           )
           Spacer(Modifier.height(8.dp))
           ProSalesTextField(
               title = "Comentarios",
               state = state.comments,
               onTextChange = {
                    onAction(DetailLeadAction.OnCommentsChange(it))
               },
               keyboardOptions = KeyboardOptions(
                   keyboardType = KeyboardType.Text,
                   imeAction = ImeAction.Done
               ),
               keyboardActions = KeyboardActions(
                   onNext = {

                   }
               ),
               colors = Color.Black,
               startIcon = Icons.Filled.Notes,
               maxLines = 104
           )
           Spacer(Modifier.height(16.dp))
           ProSalesActionButtonOutline(
               isLoading = state.isDeletingProject,
               text = "Eliminar",
               onClick = {
                    onDeleteProject()
               }
           )
       }
    }
}