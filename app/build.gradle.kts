plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.composeGuard)
}

android {
    namespace = "com.example.composetutorial"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.composetutorial"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
    // Compose Compiler Gradle 插件的配置选项
    // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-compiler.html#compose-compiler-options-dsl
    composeCompiler {
        enableStrongSkippingMode = false

        // reportsDestination = layout.buildDirectory.dir("compose_compiler")

        // stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
        freeCompilerArgs += listOf(
            "-P",
            // If this is false the Layout Inspector has limited access to the composeables
            "plugin:androidx.compose.compiler.plugins.kotlin:sourceInformation=true"
        )
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,INDEX.LIST}"
        }
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
        getByName("test") {
            java.srcDirs("src/test/kotlin")
        }

    }

    // For KSP
    /*
        applicationVariants.configureEach { variant ->
            kotlin.sourceSets {
                getByName(name) {
                    kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
                }
            }
        }
    */


}



dependencies {
    //依赖 externalLib
    implementation(project(":externalLib"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlin.serialization)

    // koin
    implementation(libs.koin.core)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.testng)
    compileOnly(libs.koin.annotations)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.koin.workmanager)
    ksp(libs.koin.compiler)

    // datastore
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)
    // navigation
    implementation(libs.androidx.navigation.compose)

    // ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.encoding)
    implementation(libs.ktor.client.cio)

    //immutableList
    implementation(libs.kotlinx.collections.immutable)

    //compose guard
    implementation(libs.composeGuard.gradle)

    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.bundles.kotest)
    androidTestImplementation(libs.bundles.androidx.test)
}