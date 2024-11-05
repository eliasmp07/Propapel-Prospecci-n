package org.propapel.prospeccion.core.presentation.ui.permissions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.propapel.prospeccion.core.presentation.ui.permissions.PermissionsService.Companion.PERMISSION_CHECK_FLOW_FREQUENCY

enum class Permission {
    POST_NOTIFICATIONS
}

enum class PermissionState {
    NOT_DETERMINED,
    GRANTED,
    DENIED;

    fun notGranted(): Boolean {
        return this != GRANTED
    }
}

interface PermissionsService {
    fun checkPermission(permission: Permission): PermissionState
    fun checkPermissionFlow(permission: Permission): Flow<PermissionState>
    suspend fun providePermission(permission: Permission)
    fun openSettingPage(permission: Permission)

    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
    }
}

internal interface PermissionDelegate {
    fun getPermissionState(): PermissionState
    suspend fun providePermission()
    fun openSettingPage()
}

internal class PermissionsServiceImpl : PermissionsService, KoinComponent {
    override fun checkPermission(permission: Permission): PermissionState {
        return try {
            return getPermissionDelegate(permission).getPermissionState()
        } catch (e: Exception) {
            println("Failed to check permission $permission")
            e.printStackTrace()
            PermissionState.NOT_DETERMINED
        }
    }

    override fun checkPermissionFlow(permission: Permission): Flow<PermissionState> {
        return flow {
            while (true) {
                val permissionState = checkPermission(permission)
                emit(permissionState)
                delay(PERMISSION_CHECK_FLOW_FREQUENCY)
            }
        }
    }

    override suspend fun providePermission(permission: Permission) {
        try {
            getPermissionDelegate(permission).providePermission()
        } catch (e: Exception) {
            println("Failed to request permission $permission")
            e.printStackTrace()
        }
    }

    override fun openSettingPage(permission: Permission) {
        println("Open settings for permission $permission")
        try {
            getPermissionDelegate(permission).openSettingPage()
        } catch (e: Exception) {
            println("Failed to open settings for permission $permission")
            e.printStackTrace()
        }
    }
}

internal fun KoinComponent.getPermissionDelegate(permission: Permission): PermissionDelegate {
    val permissionDelegate by inject<PermissionDelegate>(named(permission.name))
    return permissionDelegate
}


internal class CannotOpenSettingsException(permissionName: String) :
    Exception("Cannot open settings for permission $permissionName.")

internal class PermissionRequestException(permissionName: String) :
    Exception("Failed to request $permissionName permission.")