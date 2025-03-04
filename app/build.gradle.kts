import java.util.Properties
import java.io.File

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

val localProperties = File(rootProject.rootDir, "local.properties")
val properties = Properties().apply {
    if (localProperties.exists()) {
        load(localProperties.inputStream())
    } else {
        println("⚠️ Файл local.properties не найден! API-ключ не будет загружен.")
    }
}

android {
    namespace = "com.example.library"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.library"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "UNSPLASH_API_KEY", "\"${project.findProperty("UNSPLASH_API_KEY")}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "UNSPLASH_API_KEY", "\"${properties.getProperty("UNSPLASH_API_KEY")}\"")
        }
        debug {
            buildConfigField("String", "UNSPLASH_API_KEY", "\"${properties.getProperty("UNSPLASH_API_KEY")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Room
    implementation(libs.androidx.room.common)
    implementation("androidx.room:room-ktx:2.5.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-testing:2.5.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.1.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    runtimeOnly("androidx.compose.material:material-icons-extended:1.7.6")
    implementation("com.google.code.gson:gson:2.8.9")
    runtimeOnly("androidx.compose.foundation:foundation:1.5.1")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}