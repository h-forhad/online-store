plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
}

kotlin {
    jvmToolchain(17)

    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets.commonMain.dependencies {
        api(projects.cmpCore)

        implementation(compose.ui)
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(compose.runtime)
        implementation(compose.materialIconsExtended)
        implementation(compose.material)
        implementation(libs.material.window.size)
        implementation(compose.components.resources)

        implementation(libs.kotlin.corotines)
        implementation(libs.kotlin.datetime)
        implementation(libs.ktor.core)
        implementation(libs.ktor.serialization)
        implementation(libs.ktor.logging)
        implementation(libs.ktor.content.negotiation)

        implementation(libs.koin.core)
        implementation(libs.molecule.runtime)
        implementation(libs.kstore)

        implementation(libs.qdsfdhvh.image.loader)
        implementation(libs.google.services.map)
        implementation(libs.decompose.router)
        implementation(libs.decompose.compose)
        implementation(libs.calendar.compose.basis)
        implementation(libs.calendar.compose.ranges) // includes basis
        implementation(libs.calendar.compose.pager) // includes basis
        implementation(libs.calendar.compose.datepicker) // includes pager + ranges
    }

    sourceSets.androidMain.dependencies {
        implementation(libs.androidx.activity.compose)
        implementation(libs.ktor.client.android)
        implementation(libs.ktor.client.okhttp)
        implementation(libs.kstore.file)
        implementation(libs.androidx.appcompat)
    }

    sourceSets.jvmMain.dependencies {
        implementation(compose.desktop.currentOs)
        implementation(libs.ktor.client.cio)
        implementation(libs.kstore.file)
        implementation(libs.okio)
    }

    sourceSets.iosMain.dependencies {
        implementation(libs.kstore.file)
        implementation(libs.ktor.client.ios)
    }
}

android {
    namespace = "com.greenrobotdev.favily.onlinestore"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}