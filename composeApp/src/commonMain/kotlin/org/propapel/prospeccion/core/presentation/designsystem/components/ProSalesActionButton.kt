package org.propapel.prospeccion.core.presentation.designsystem.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ProSalesActionButton(
    text: String,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    textColor: Color = Color.Black,
    shape: Shape = RoundedCornerShape(12.dp),
    enabled: Boolean = true,
    colors: ButtonColors =  ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.Black
    ),
    icon: ImageVector? = null,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier
            .height(IntrinsicSize.Min).pointerHoverIcon(PointerIcon.Hand),
        enabled = enabled,
        border = border,
        shape = shape,
        colors =colors,
        onClick = onClick,
        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                if (icon != null){
                    Icon(imageVector = icon, contentDescription = null)
                }
                Text(
                    text = text,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .alpha(if (isLoading) 0f else 1f),
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun ProSalesActionButtonOutline(
    text: String,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    borderColor: Color = MaterialTheme.colorScheme.onBackground,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = borderColor
        ),
        shape = shape,
        modifier = modifier
            .height(IntrinsicSize.Min).pointerHoverIcon(PointerIcon.Hand)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                if (icon != null){
                    Icon(imageVector = icon, contentDescription = null)
                }
                Text(
                    text = text,
                    color = textColor,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .alpha(if (isLoading) 0f else 1f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}