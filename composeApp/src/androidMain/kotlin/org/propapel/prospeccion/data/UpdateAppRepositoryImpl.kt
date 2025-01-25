package org.propapel.prospeccion.data

import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.propapel.prospeccion.updateApk.UpdateAppRepository
import java.io.File
import java.net.URL

class UpdateAppRepositoryImpl(
    private val context: Context,
    private val remoteConfig: FirebaseRemoteConfig
) : UpdateAppRepository {
    companion object {
        const val APK_LINK = "apk_link"
        const val MIN_VERSION = "min_version"
    }

    init {
        remoteConfig.fetchAndActivate()
    }

    override suspend fun getApkLink(): String {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val apkLink = remoteConfig.getString(APK_LINK)
        return apkLink.ifBlank { "" }
    }

    override suspend fun getMinAllowedVersion(): List<Int> {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val minVersion = remoteConfig.getString(MIN_VERSION)
        return if (minVersion.isBlank()) listOf(
            0,
            0,
            0
        )
        else minVersion.split(".").map { it.toInt() }
    }

    override suspend fun getCurrentVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                0
            )
            packageInfo.versionName.split(".").map { it.toInt() }
        } catch (e: Exception) {
            listOf(
                0,
                0,
                0
            )
        }
    }

    override suspend fun downloadApk(
        onProgress: (Int) -> Unit
    ): File? {
        return withContext(Dispatchers.IO) {
            remoteConfig.fetch(0)
            remoteConfig.activate().await()
            val apkLink = remoteConfig.getString(APK_LINK).ifBlank { "" }
            val apkFile = File(
                context.cacheDir,
                "app_update.apk"
            )
            try {
                val connection = URL(apkLink).openConnection()
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