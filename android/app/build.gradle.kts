plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.vpn.clienta"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vpn.clienta"
        minSdk = 24
        targetSdk = 34

        versionCode = 1
        versionName = "1.0"

        // 🔴 FAIL FAST: если ресурсы битые — билд падает сразу
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {

        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }

        release {

            isMinifyEnabled = true

            // 🔴 PRO GUARD (чтобы не падало на релизе)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"

        // 🔴 FAIL EARLY KOTLIN CHECK
        freeCompilerArgs += listOf(
            "-Xjsr305=strict",
            "-Xstrict-java-nullability-assertions"
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            // 🔴 FIX COMMON CI FAILS
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // CORE
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.2")

    // COMPOSE
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")

    debugImplementation("androidx.compose.ui:ui-tooling")

    // VIEWMODEL
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // NETWORK
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // CAMERA X (QR)
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")

    // ML KIT
    implementation("com.google.mlkit:barcode-scanning:17.3.0")

    // JSON SAFE PARSING
    implementation("com.google.code.gson:gson:2.11.0")
}