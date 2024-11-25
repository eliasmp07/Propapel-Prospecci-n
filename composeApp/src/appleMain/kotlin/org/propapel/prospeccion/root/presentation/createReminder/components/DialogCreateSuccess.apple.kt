@file:OptIn(ExperimentalForeignApi::class)

package org.propapel.prospeccion.root.presentation.createReminder.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.copy
import org.jetbrains.compose.resources.DrawableResource
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIButton
import platform.UIKit.UIColor
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UITableView
import platform.UIKit.UIView
import platform.UIKit.UIViewContentMode
import platform.UserNotifications.UNMutableNotificationContent

@Composable
actual fun DialogCreateSuccess(
    title: String,
    message: String,
    icon: ImageVector?,
    image: DrawableResource?,
    modifier: Modifier,
    textButton: String,
    onDismissRequest: () -> Unit
) {
    UIKitView(
        modifier = modifier,
        factory = {
            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            val alart = UIAlertController.alertControllerWithTitle(
                title = title,
                message = message,
                preferredStyle = UIAlertControllerStyleAlert
            )
            val action = UIAlertAction.actionWithTitle(
                title = textButton,
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
        update = {}
    )
}