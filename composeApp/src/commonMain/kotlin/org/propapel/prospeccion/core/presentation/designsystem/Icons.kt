package org.propapel.prospeccion.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.vectorResource
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.email
import prospeccion.composeapp.generated.resources.eye_closed
import prospeccion.composeapp.generated.resources.eye_opened
import prospeccion.composeapp.generated.resources.lock

val EmailIcon: ImageVector
    @Composable
    get() = vectorResource(Res.drawable.email)

val EyeClosedIcon: ImageVector
    @Composable
    get() =vectorResource(Res.drawable.eye_closed)

val EyeOpenedIcon: ImageVector
    @Composable
    get() =vectorResource(Res.drawable.eye_opened)

val LockIcon : ImageVector
    @Composable
    get() =vectorResource(Res.drawable.lock)
