package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.TypeSpecimen
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.vectorResource
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.email_outline

@Composable
fun InfoLeadPageItem(
    customer: Customer,
    onAction: (DetailLeadAction) -> Unit
){
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