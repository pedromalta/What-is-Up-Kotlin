import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.mokkery)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions{
        // Suppress warnings for using actual/expected classes
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "WhatIsUpKotlin"
            isStatic = true
            binaryOption("bundleId", "whatisup.kotlin.app")
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
    
    sourceSets {

        dependencies {
            debugImplementation(compose.uiTooling)
            add("kspAndroid", libs.androidx.room.compiler)
            add("kspIosSimulatorArm64", libs.androidx.room.compiler)
            add("kspIosX64", libs.androidx.room.compiler)
            add("kspIosArm64", libs.androidx.room.compiler)

        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            //implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.window.size)

            implementation(libs.bundles.compose.common)
            implementation(libs.bundles.koin.common)
            implementation(libs.bundles.reaktive.common)
            implementation(libs.bundles.ktor.common)
            implementation(libs.bundles.room.common)

            implementation(libs.napier)
            implementation(libs.store)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.junit)
            implementation(libs.reaktive.testing)
        }

    }
}

android {
    namespace = "whatisup.kotlin.app"
    compileSdk = libs.versions.android.compilesdk.get().toInt()

    defaultConfig {
        applicationId = "whatisup.kotlin.app"
        minSdk = libs.versions.android.minsdk.get().toInt()
        targetSdk = libs.versions.android.targetsdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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


