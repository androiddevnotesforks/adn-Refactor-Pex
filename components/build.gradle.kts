import commons.addComposeDependencies
import commons.addDefaultComposeDependencies
import commons.implementation
import dependencies.Dependencies
import dependencies.Versions

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.javaVersionName
        targetCompatibility = AndroidConfig.javaVersionName
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(Modules.CORE))
    implementation(project(Modules.BASE))
    implementation(project(Modules.DOMAIN))

    addDefaultComposeDependencies()
    addComposeDependencies()

    implementation(Dependencies.accompanistCoil)
    implementation(Dependencies.accompanistPager)
    implementation(Dependencies.shimmer)
    implementation(Dependencies.lottie)
    implementation(Dependencies.accompanistPermissions)
    implementation(Dependencies.accompanistInsets)
    implementation(Dependencies.systemUiController)
}