package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

@Composable
actual fun DialogConfirmOption(
    modifier: Modifier,
    textButton: String,
    onAcceptOption: () -> Unit,
    title: String,
    description: String,
    onDismissRequest: () -> Unit
) {
    UIKitView(
        modifier = modifier,
        factory = {
            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            val alart = UIAlertController.alertControllerWithTitle(
                title = title,
                message = description,
                preferredStyle = UIAlertControllerStyleAlert
            )
            val action = UIAlertAction.actionWithTitle(
                title = textButton,
                style = UIAlertActionStyleDefault,
                handler = {
                    alart.dismissViewControllerAnimated(flag = true, completion = null)
                    onAcceptOption()
                }
            )
            val cancelAction = UIAlertAction.actionWithTitle(
                title = "Cancelar",
                style = UIAlertActionStyleCancel,
                handler = {
                    alart.dismissViewControllerAnimated(flag = true, completion = null)
                    onDismissRequest()
                }
            )
            alart.addAction(action)
            alart.addAction(cancelAction)
            rootViewController?.presentViewController(
                alart, animated = true , completion =null
            )
            alart.view
        },
        update = {

        }
    )
}