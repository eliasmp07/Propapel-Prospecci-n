rootProject.name = "Prospeccion"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        maven("https://jogamp.org/deployment/maven")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }

        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://jogamp.org/deployment/maven")
        mavenCentral()
    }
}

include(":composeApp")