package org.propapel.prospeccion.core.presentation.designsystem
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val extraSmall: Dp = 0.dp,
    val small1: Dp = 0.dp,
    val small2: Dp = 0.dp,
    val small3: Dp = 0.dp,
    val medium1: Dp = 0.dp,
    val medium2: Dp = 0.dp,
    val medium3: Dp = 0.dp,
    val large: Dp = 0.dp,
    val buttonHeight: Dp = 0.dp,
    val logoSize: Dp = 0.dp,
    val sizeCardItems: Dp = 0.dp,
    val sizeImageItem: Dp = 0.dp,
    val textFieldHeight: Dp = 0.dp,
    val  sizeImageCreateMateria: Dp = 0.dp,
    val sizeBackgroundImage: Dp =0.dp,
    val paddingColumn: Dp = 0.dp,
    val paddingTopColumn: Dp = 0.dp
)

val CompactSmallDimens = Dimens(
    small1 = 4.dp, // Reducido de 6.dp a 4.dp para pantallas compactas
    small2 = 3.dp, // Reducido de 5.dp a 3.dp para pantallas compactas
    small3 = 6.dp, // Reducido de 8.dp a 6.dp para pantallas compactas
    medium1 = 12.dp, // Reducido de 15.dp a 12.dp para pantallas compactas
    medium2 = 20.dp, // Reducido de 26.dp a 20.dp para pantallas compactas
    medium3 = 24.dp, // Reducido de 30.dp a 24.dp para pantallas compactas
    large = 36.dp, // Reducido de 45.dp a 36.dp para pantallas compactas
    buttonHeight = 40.dp, // Reducido de 38.dp a 32.dp para pantallas compactas
    logoSize = 70.dp, // Reducido de 55.dp a 44.dp para pantallas compactas
    sizeCardItems = 280.dp, // Reducido de 310.dp a 280.dp para pantallas compactas
    sizeImageItem = 120.dp, // Reducido de 130.dp a 120.dp para pantallas compactas
    textFieldHeight = 58.dp, //Tamaño de los textField,
    sizeImageCreateMateria = 120.dp,
    sizeBackgroundImage = 230.dp,
    paddingColumn = 30.dp,
    paddingTopColumn = 150.dp
)

val CompactMediumDimens = Dimens(
    small1 = 6.dp, // Reducido de 8.dp a 6.dp para pantallas compactas
    small2 = 10.dp, // Reducido de 13.dp a 10.dp para pantallas compactas
    small3 = 14.dp, // Reducido de 17.dp a 14.dp para pantallas compactas
    medium1 = 18.dp, // Reducido de 22.dp a 18.dp para pantallas compactas
    medium2 = 24.dp, // Reducido de 28.dp a 24.dp para pantallas compactas
    medium3 = 28.dp, // Reducido de 33.dp a 28.dp para pantallas compactas
    large = 52.dp, // Reducido de 62.dp a 52.dp para pantallas compactas
    logoSize = 100.dp, // Reducido de 50.dp a 40.dp para pantallas compactas
    buttonHeight = 45.dp, // Reducido de 45.dp a 40.dp para pantallas compactas
    sizeCardItems = 300.dp, // Reducido de 322.dp a 300.dp para pantallas compactas
    sizeImageItem = 125.dp, // Reducido de 135.dp a 125.dp para pantallas compactas
    textFieldHeight = 64.dp,
    sizeImageCreateMateria = 100.dp,
    sizeBackgroundImage = 250.dp,
    paddingColumn = 28.dp,
    paddingTopColumn = 200.dp
)

val CompactDimens = Dimens(
    small1 = 8.dp, // Reducido de 10.dp a 8.dp para pantallas compactas
    small2 = 12.dp, // Reducido de 15.dp a 12.dp para pantallas compactas
    small3 = 16.dp, // Reducido de 20.dp a 16.dp para pantallas compactas
    medium1 = 24.dp, // Reducido de 30.dp a 24.dp para pantallas compactas
    medium2 = 30.dp, // Reducido de 36.dp a 30.dp para pantallas compactas
    medium3 = 36.dp, // Reducido de 40.dp a 36.dp para pantallas compactas
    large = 70.dp, // Reducido de 80.dp a 70.dp para pantallas compactas
    logoSize = 80.dp, // Reducido de 94.dp a 80.dp para pantallas compactas
    buttonHeight = 50.dp, // Reducido de 55.dp a 50.dp para pantallas compactas
    sizeCardItems = 320.dp, // Reducido de 334.dp a 320.dp para pantallas compactas
    sizeImageItem = 130.dp, // Reducido de 140.dp a 130.dp para pantallas compactas
    sizeBackgroundImage = 250.dp,
    paddingColumn = 58.dp,
    paddingTopColumn = 150.dp
)


val MediumDimens = Dimens(
    small1 = 10.dp,
    small2 = 15.dp,
    small3 = 20.dp,
    medium1 = 30.dp,
    medium2 = 36.dp,
    medium3 = 40.dp,
    large = 110.dp,
    buttonHeight = 60.dp,
    logoSize = 101.dp,
    sizeCardItems = 346.dp,
    sizeImageItem = 145.dp,
)

val ExpandedDimens = Dimens(
    small1 = 15.dp,
    small2 = 20.dp,
    small3 = 25.dp,
    medium1 = 35.dp,
    medium2 = 30.dp,
    medium3 = 45.dp,
    large = 130.dp,
    buttonHeight = 65.dp,
    logoSize = 118.dp,
    sizeCardItems = 358.dp,
    sizeImageItem = 150.dp,
)