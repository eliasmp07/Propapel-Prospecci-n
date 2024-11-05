package org.propapel.prospeccion.notifications

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import org.propapel.prospeccion.core.presentation.ui.permissions.Permission
import org.propapel.prospeccion.core.presentation.ui.permissions.PermissionDelegate
import org.propapel.prospeccion.core.presentation.ui.permissions.PermissionRequestException
import org.propapel.prospeccion.core.presentation.ui.permissions.PermissionState
import org.propapel.prospeccion.notifications.utils.checkPermissions
import org.propapel.prospeccion.notifications.utils.openAppSettingsPage
import org.propapel.prospeccion.notifications.utils.providePermissions

class NotificationPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
): PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermissions(context, activity,
                             listOf(Manifest.permission.POST_NOTIFICATIONS))
        } else {
            PermissionState.GRANTED
        }
    }

    override suspend fun providePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.value.providePermissions(listOf(Manifest.permission.POST_NOTIFICATIONS)) {
               throw PermissionRequestException(Permission.POST_NOTIFICATIONS.name)
           }
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.POST_NOTIFICATIONS)
    }
}