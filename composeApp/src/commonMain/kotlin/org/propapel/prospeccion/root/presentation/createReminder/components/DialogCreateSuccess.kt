@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.createReminder.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline

@Composable
fun DialogCreateSuccess(
    title: String,
    message: String,
    icon: ImageVector? = null,
    image: DrawableResource? = null,
    modifier: Modifier = Modifier,
    textButton: String = "Aceptar",
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
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
                    if(icon != null){
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                    }
                    if (image != null) {
                        Image(
                            painter = painterResource(image),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    ProSalesActionButtonOutline(
                        text = textButton,
                        onClick = {
                            onDismissRequest()
                        }
                    )
                }
            }
        },
    )
}