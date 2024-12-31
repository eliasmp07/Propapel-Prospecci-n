package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Visibility
import androidx.compose.material.icons.sharp.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.propapel.prospeccion.core.presentation.designsystem.EyeClosedIcon
import org.propapel.prospeccion.core.presentation.designsystem.EyeOpenedIcon
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiDarkRed

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProspeccionTextFieldAnimation(
    modifier: Modifier = Modifier,
    hint: String = "hint",
    text: String,
    error: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isPassword: Boolean = false,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    onTextChange: (String) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }

    var hidePassword by remember { mutableStateOf(true) }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val showHintAbove by remember {
        derivedStateOf {
            isFocused || text.isNotEmpty()
        }
    }


    val onTextChangeState by rememberUpdatedState(onTextChange)
    Column {
        BasicTextField(
            value = text,
            onValueChange = {
                onTextChangeState(it)
            },
            modifier = modifier,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            visualTransformation =  if (isPassword && hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = textFieldTextStyle(),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            decorationBox = { innerTextField ->
                SharedTransitionLayout {
                    AnimatedContent(
                        targetState = showHintAbove,
                        transitionSpec = {
                            EnterTransition.None togetherWith ExitTransition.None
                        },
                        label = "hintAnimation"
                    ) { showHintAbove ->
                        Column {
                            Box(Modifier.padding(start = 2.dp)) {
                                InvisibleTextAsPlaceholder(exteriorHintTextStyle())
                                if (showHintAbove || text.isNotEmpty()) {
                                    TextAsIndividualLetters(
                                        animatedContentScope = this@AnimatedContent,
                                        text = hint,
                                        style = exteriorHintTextStyle(),
                                    )
                                }
                            }
                            Spacer(Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .sharedElement(
                                        rememberSharedContentState(key = "input"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                    .defaultMinSize(minWidth = 300.dp)
                                    .background(
                                        Color.Transparent
                                    )
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 8.dp
                                    ),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (startIcon != null) {
                                        Icon(
                                            imageVector = startIcon,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                    }
                                    if (!showHintAbove && text.isEmpty()) {
                                        TextAsIndividualLetters(
                                            animatedContentScope = this@AnimatedContent,
                                            text = hint,
                                            style = interiorHintTextStyle(),
                                        )
                                    }
                                    innerTextField()
                                    if (endIcon != null) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            imageVector = endIcon,
                                            tint = Color.Black,
                                            contentDescription = null,
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                    }
                                    if (isPassword) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            imageVector = if (hidePassword) EyeOpenedIcon else EyeClosedIcon,
                                            contentDescription = null,
                                            tint = Color.Black,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .clickable { hidePassword = !hidePassword }.pointerHoverIcon(PointerIcon.Hand)

                                        )
                                    }
                                }
                            }
                            HorizontalDivider(
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        )
        AnimatedVisibility(
            visible = error != null,
            enter = scaleIn(),
            exit = scaleOut()
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FormError()
                Spacer(modifier = Modifier.width(5.dp))
                androidx.compose.material.Text(
                    text = error?:"",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun FormError() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(SoporteSaiDarkRed)
            .size(10.dp)
    )
}

@Composable
fun InvisibleTextAsPlaceholder(style: TextStyle) {
    Text(
        text = "",
        modifier = Modifier.alpha(0f),
        style = style,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TextAsIndividualLetters(
    animatedContentScope: AnimatedContentScope,
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
) {
    Row(modifier) {
        text.forEachIndexed { index, letter ->
            Text(
                text = "$letter",
                modifier = Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "hint_$index"),
                    animatedVisibilityScope = animatedContentScope,
                    boundsTransform = { _, _ ->
                        spring(
                            stiffness = 25f * (text.length - index),
                            dampingRatio = Spring.DampingRatioLowBouncy,
                        )
                    }
                ),
                style = style,
            )
        }
    }
}

@ReadOnlyComposable
@Composable
fun textFieldTextStyle() = MaterialTheme.typography.labelLarge.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .9f),
)

@ReadOnlyComposable
@Composable
fun exteriorHintTextStyle() = MaterialTheme.typography.labelLarge.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    color = Color.Black,
)

@ReadOnlyComposable
@Composable
fun interiorHintTextStyle() = textFieldTextStyle().copy(
    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .4f),
)