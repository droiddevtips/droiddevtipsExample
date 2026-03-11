@file:Suppress("UnstableApiUsage")

include(":pinchtozoom")


include(":musicplayer")


include(":app")
include(":admobnextgenads")
include(":droidDevTipsTheme")
include(":masteringcomposetheme")
include(":multiscreensupport")
include(":appWindowSizeAndOrientationDetector")
include(":floatingtabbarandpip")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DroidDevTipsExamples"

