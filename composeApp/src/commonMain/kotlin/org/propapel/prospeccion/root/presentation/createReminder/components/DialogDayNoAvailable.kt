package org.propapel.prospeccion.root.presentation.createReminder.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction

@Composable
fun DialogDayNoAvailable(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            ElevatedCard(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier.background(
                        Color.White
                    ).padding(16.dp)
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            onDismissRequest()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Momento no disponible",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Ups! Parece que el momento seleccionado no está disponible, por favor selecciona otro momento.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    ProSalesActionButtonOutline(
                        text = "Aceptar",
                        onClick = {
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    )
}