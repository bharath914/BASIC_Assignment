import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.9.21"
}

android {
    namespace = "com.bharath.basicassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bharath.basicassignment"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildFeatures.buildConfig = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField(
            "String",
            name = "SUPABASE_KEY",
            value = properties.getProperty("SUPABASE_KEY")
        )
        buildConfigField(
            "String",
            name = "SUPABASE_PROJECT_URL",
            value = properties.getProperty("SUPABASE_PROJECT_URL")
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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


    implementation(libs.androidx.core.splashscreen)

    /*
    Supabase server
     */


    implementation(platform(libs.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.storage.kt)

    /*
  Room Database
   */
    //noinspection GradleDependency
    implementation(libs.androidx.room.runtime)
    //noinspection GradleDependency
    ksp(libs.androidx.room.compiler)
//    implementation("androidx.room:room-coroutines:2.1.0-alpha04")
    // Kotlin Extensions and Coroutines support for Room
    //noinspection GradleDependency
    implementation(libs.androidx.room.ktx)
    //noinspection GradleDependency


    // glide
    implementation(libs.compose)
    implementation("io.coil-kt:coil-compose:2.5.0")


    implementation(libs.androidx.navigation.compose)


    /*
    Life cycle
     */
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)


    /*
  Splash Screen
   */
    implementation(libs.androidx.core.splashscreen)

    /*
    Hilt
     */

    implementation(libs.hilt.android)
    ksp(libs.dagger.compiler) // Dagger compiler
    ksp(libs.hilt.compiler)   // Hilt compiler


    /*
    navigation
     */
    implementation(libs.androidx.hilt.navigation.compose)



    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.utils)


    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)


    implementation(libs.androidx.material)
}

