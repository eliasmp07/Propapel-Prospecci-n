package org.propapel.prospeccion.root.presentation.updateProfile

sealed interface UpdateProfileSMAction {
    data object OnBackClick: UpdateProfileSMAction
    data object OnUpdateClick: UpdateProfileSMAction
    data object HideError: UpdateProfileSMAction
    data object HideSuccess: UpdateProfileSMAction
    data class OnChangeImageProfile(val image: String): UpdateProfileSMAction
}