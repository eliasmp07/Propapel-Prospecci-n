package org.propapel.prospeccion.updateApk

import android.content.Context
import android.icu.util.VersionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
/*
interface UpdateAppRepository{
    suspend fun checkForUpdate()
    suspend fun downloadApk(url: String, onProgress: (Int) -> Unit): File?
}

class UpdateAppRepositoryImpl (
    private val context: Context
): UpdateAppRepository {
    override suspend fun checkForUpdate() {
        val currentVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        val latestVersion = getLatestVersionFromServer() // Reemplazar con la lógica para consultar al servidor

        if (latestVersion > currentVersion) {
            // Actualización disponible
        } else {
            // Ya está en la última versión
        }
    }

    override suspend fun downloadApk(
        url: String,
        onProgress: (Int) -> Unit
    ): File? {
        return withContext(Dispatchers.IO) {
            val apkFile = File(
                context.cacheDir,
                "app_update.apk"
            )

            try {
                val connection = URL(url).openConnection()
                connection.connect()

                val fileLength = connection.contentLength
                val input = connection.getInputStream()
                val output = apkFile.outputStream()

                input.use { inputStream ->
                    output.use { fileOutputStream ->
                        val data = ByteArray(4096)
                        var total: Long = 0
                        var count: Int
                        while (inputStream.read(data).also { count = it } != -1) {
                            total += count
                            fileOutputStream.write(
                                data,
                                0,
                                count
                            )
                            val progress = (total * 100 / fileLength).toInt()
                            onProgress(progress)
                        }
                    }
                }
                apkFile
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}
 */