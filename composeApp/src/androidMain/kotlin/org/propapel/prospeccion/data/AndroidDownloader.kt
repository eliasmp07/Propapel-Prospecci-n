package org.propapel.prospeccion.data

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import org.propapel.prospeccion.domain.Downloader

class AndroidDownloader(
    private val context: Context
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/vnd.android.package-archive") // MIME type para APK
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("app-release.apk") // Nombre que se mostrará en la notificación
            .setDescription("Descargando la última versión de la aplicación.")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "app-release.apk") // Ruta del archivo
        return downloadManager.enqueue(request)
    }
}