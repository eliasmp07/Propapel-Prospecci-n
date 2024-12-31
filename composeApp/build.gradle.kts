import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kspCompose)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting
        task("testClasses")
        androidMain.dependencies {
            implementation(compose.preview)

            // Permissions
            implementation("com.google.accompanist:accompanist-permissions:0.34.0")
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
          // implementation(libs.ktor.client.okhttp)
            implementation(libs.core.splashscreen)

        }
        commonMain.dependencies {
            api("io.github.kevinnzou:compose-webview-multiplatform:1.9.40")

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)
            implementation(libs.viewmodel.compose)

            implementation(compose.materialIconsExtended)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.cio)

            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.mp)

            implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.5.0")

            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)

            implementation(libs.kotlinx.datetime)

            implementation(libs.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            //Time picker
            implementation("network.chaintech:kmp-date-time-picker:1.0.6")

            //FILE PICKER
            implementation("com.mohamedrejeb.calf:calf-file-picker:0.5.3")
            implementation("com.mohamedrejeb.calf:calf-file-picker-coil:0.5.1")

            api(libs.datastore.preferences)
            api(libs.datastore)

            implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
            implementation(libs.kotlinx.datetime)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            implementation("io.github.thechance101:chart:Beta-0.0.5")
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-compose")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha03")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-beta02")
            implementation("io.github.alexzhirkevich:compottie:1.1.2")
            implementation("io.github.vinceglb:filekit-core:0.5.0")
            implementation("io.github.vinceglb:filekit-compose:0.5.0")

            //Lottie
            implementation("io.github.ismai117:kottie:2.0.0")
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.java)
            implementation( "org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")

        }
    }
}

android {
    namespace = "org.propapel.prospeccion"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.propapel.prospeccion"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.ktor.client.java)
}


compose.desktop {
    application {
        mainClass = "org.propapel.prospeccion.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Elias"
            packageVersion = "1.0.0"
            description = "Aplicacion de prospeccion"
            copyright = "© 2020 Propapel. Todos los derechos reservados."
            vendor = "Propapel"
            includeAllModules = true//Parametro necesario para que la aplicacino ejecute el guardado de sesion
            windows{
                iconFile.set(project.file("icon.ico"))
                perUserInstall = true
                console = false
                shortcut = true
                packageVersion = "1.0.0"
                msiPackageVersion = "1.0.0"
                exePackageVersion = "1.0.0"
                packageName = "Prospeccion"
            }
        }

        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED") // recommended but not necessary

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }
    }
}
