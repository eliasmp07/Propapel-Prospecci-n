package org.propapel.prospeccion.root.presentation.leads.components.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.propapel.prospeccion.root.domain.models.Customer

@Composable
fun ItemLead(
    customer: Customer,
    onClick: (String) -> Unit
){
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onClick(customer.idCustomer.toString())
        }
    ){
        Row(
            modifier = Modifier.clip(RoundedCornerShape(30.dp)).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp))
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Column {
                Text(text = "Empresa", style = MaterialTheme.typography.titleMedium)
                Text(text = customer.companyName, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Numero de contacto", style = MaterialTheme.typography.titleMedium)
                Text(text = customer.phoneNumber, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}