package org.propapel.prospeccion.root.presentation.createReminder.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

@Composable
actual fun DialogDayNoAvailable(
    modifier: Modifier,
    onDismissRequest: () -> Unit
) {
    UIKitView(
        modifier = modifier,
        factory = {
            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            val alart = UIAlertController.alertControllerWithTitle(
                title = "Momento no disponible",
                message = "Ups! Parece que el momento seleccionado no está disponible, por favor selecciona otro momento.",
                preferredStyle = UIAlertControllerStyleAlert
            )
            val action = UIAlertAction.actionWithTitle(
                title = "Aceptar",
                style = UIAlertActionStyleDefault,
                handler = {
                    alart.dismissViewControllerAnimated(flag = true, completion = null)
                    onDismissRequest()
                }
            )

            alart.addAction(action)
            rootViewController?.presentViewController(
                alart, animated = true , completion =null
            )
            alart.view
        },
        update = {

        }
    )
}