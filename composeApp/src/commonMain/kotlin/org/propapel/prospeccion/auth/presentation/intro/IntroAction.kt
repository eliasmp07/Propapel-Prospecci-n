package org.propapel.prospeccion.auth.presentation.intro

sealed interface IntroAction {
    data object OnLoginClick : IntroAction
}