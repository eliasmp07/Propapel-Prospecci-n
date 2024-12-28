@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.webView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.ActualWebView
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.root.presentation.createProject.componetns.isVisibleBottomSheet

@Composable
actual fun WebViewScreenView(
    modifier: Modifier,
    url: String,
    onBackClick: () -> Unit
) {
    val webState = rememberWebViewState(url)
    val loading = webState.loadingState
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(
        )
    )

    val navigator = rememberWebViewNavigator()

    var pageTitle by remember { mutableStateOf("") }

    LaunchedEffect(webState) {
        delay(2000)
        pageTitle = webState.pageTitle ?: if (url.contains("propapel.mx")) "Propapel" else ""
    }


    var successCopyUrl by remember {
        mutableStateOf(
            false
        )
    }

    LaunchedEffect(successCopyUrl) {
        if (successCopyUrl) {
            delay(2000) // Espera de 2 segundos
            successCopyUrl = false
        }
    }

    val clipboardManager = LocalClipboardManager.current

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = pageTitle,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = url.removeRange(
                                    0,
                                    8
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick,
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (scaffoldState.bottomSheetState.isVisible){
                                    scope.launch {
                                        scaffoldState.bottomSheetState.hide()
                                    }
                                }else{
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }

                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        )
                    }
                )
                if (loading is LoadingState.Loading) {
                    LinearProgressIndicator(
                        progress = { loading.progress },
                        color = SoporteSaiBlue,
                        modifier = Modifier.fillMaxWidth().height(3.dp),
                    )
                }

            }
        },
        sheetContent = {
            HorizontalDivider()
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ItemActionWebView(
                        onClickAction = {
                            if (scaffoldState.isVisibleBottomSheet) {
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                            navigator.reload()
                        },
                        textAction = "Recargar",
                        icon = Icons.Default.Refresh
                    )
                    ItemActionWebView(
                        isEnable = navigator.canGoBack,
                        onClickAction = {
                            if (scaffoldState.isVisibleBottomSheet) {
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                            navigator.navigateBack()
                        },
                        textAction = "Regresar",
                        icon = Icons.AutoMirrored.Filled.ArrowLeft
                    )
                    ItemActionWebView(
                        isEnable = navigator.canGoForward,
                        onClickAction = {
                            if (scaffoldState.isVisibleBottomSheet) {
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                            navigator.navigateForward()
                        },
                        textAction = "Adelante",
                        icon = Icons.AutoMirrored.Filled.ArrowRight
                    )
                    ItemActionWebView(
                        onClickAction = {
                            if (scaffoldState.isVisibleBottomSheet) {
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                            val text = buildAnnotatedString {
                                withStyle(style = SpanStyle()) {
                                    append(url)
                                }
                            }
                            successCopyUrl = true
                            clipboardManager.setText(text)
                        },

                        textAction = "Copiar enlace",
                        icon = Icons.Default.Link
                    )
                }
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.TravelExplore,
                            contentDescription = null,
                            tint = Color.Black
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                        Text(
                            text = "Abrir en un explorador externo",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding())
            ){

                WebView(
                    state = webState,
                    navigator = navigator,
                    modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding())
                )
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    visible = successCopyUrl,
                    enter = slideInVertically(
                        initialOffsetY = { it } // Aparece de abajo hacia arriba
                    ) + fadeIn(),
                    exit = slideOutVertically(
                        targetOffsetY = { it } // Desaparece de arriba hacia abajo
                    ) + fadeOut(),
                ) {
                    ElevatedCard(
                        modifier = Modifier.padding(10.dp).align(Alignment.BottomCenter),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = SuccessGreen
                        )
                    ){
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                        ){
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Text(
                                text = "Enlace copiado en el portapapeles",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

        }
    )
}