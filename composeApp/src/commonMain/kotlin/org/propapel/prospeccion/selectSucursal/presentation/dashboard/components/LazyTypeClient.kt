package org.propapel.prospeccion.selectSucursal.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.selectSucursal.domain.model.CustomerUser
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.customer_person

@Composable
fun TypeClientLazy(
    modifier: Modifier = Modifier,
    categoryItem: List<CustomerUser>
) {

    val typesClients = calculateTypeCustomers(categoryItem)
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(typesClients) {
            Card(
                colors = CardColors(
                    containerColor = Color(0xFFFEF9E4),
                    contentColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                ),
                modifier = Modifier.size(
                    width = 120.dp,
                    height = 134.dp
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(it.image),
                        contentDescription = null,
                        modifier = Modifier.size(
                            width = 71.dp,
                            height = 66.dp
                        )
                    )
                    Text(
                        text = it.type,
                        fontSize = 16.sp,
                        color = Color(0xFF1E2742)
                    )
                    Text(
                        text = it.total.toString(),
                        color = Color(0xFFFB7D8A),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

fun calculateTypeCustomers(
    customers: List<CustomerUser>,
): List<TypeClient> {
    var nuevos = 0
    var desarrollo = 0
    var recuperacion = 0

    customers.forEach { customer ->
        when {
            customer.typeOfClient.contains(TypeOfClient.NUEVO.name) -> nuevos++
            customer.typeOfClient.contains(TypeOfClient.DESARROLLO.name) -> desarrollo++
            else -> recuperacion++
        }
    }
    return listOf(
        TypeClient(
            type = "Nuevos",
            Res.drawable.customer_person,
            nuevos
        ),
        TypeClient(
            type = "Desarrollo",
            Res.drawable.customer_person,
            desarrollo
        ),
        TypeClient(
            type = "Recuperacion",
            Res.drawable.customer_person,
            recuperacion
        )
    )
}

data class TypeClient(
    val type: String = "",
    val image: DrawableResource,
    val total: Int = 0
)