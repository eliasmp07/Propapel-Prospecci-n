package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline

@Composable
fun DialogConfirmOption(
    modifier: Modifier = Modifier,
    textButton: String,
    onAcceptOption: () -> Unit,
    title: String,
    description: String,
    onDismissRequest: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            ElevatedCard(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
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
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = description,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ){
                        ProSalesActionButtonOutline(
                            modifier = Modifier.weight(1f),
                            text = textButton,
                            onClick = {
                                onAcceptOption()
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        ProSalesActionButtonOutline(
                            modifier = Modifier.weight(1f),
                            text = "Cancelar",
                            onClick = {
                                onDismissRequest()
                            }
                        )
                    }
                }
            }
        }
    )
}