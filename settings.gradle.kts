pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://phonepe.mycloudrepo.io/public/repositories/phonepe-intentsdk-android")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://phonepe.mycloudrepo.io/public/repositories/phonepe-intentsdk-android")
    }
}

rootProject.name = "PhonePePayPageSDK"
include(":app")
 