plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.ali.hacker_demo"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = "\"https://hacker-news.firebaseio.com/v0/\""
        )
    }

    /** used for unit-test run with Junit5 */
    tasks.withType<Test> {
        useJUnitPlatform()
    }

    sourceSets {
        val main by getting
        main.res.srcDirs("src/main/res")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
//        dataBinding = true
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}


dependencies {
    implementation(Kotlin.ktx)
    implementation(Kotlin.coroutine_core)
    implementation(Kotlin.coroutines_android)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(Retrofit2.retrofit)
    implementation(Retrofit2.moshi)

    implementation(Navigation.ktx)
    implementation(Navigation.ui_ktx)
    implementation(Navigation.fragment)

    implementation(UiMaterial.appcompat)
    implementation(UiMaterial.material)
    implementation(UiMaterial.recyclerview)

    testImplementation(Mockk.mockk)
    testImplementation(Mockk.agent_jvm)

    testImplementation(Junit5.jupiter)
    testImplementation(Junit5.suite)
    testRuntimeOnly(Junit5.vintage_engine)


    testImplementation(OtherLib.turbin)
    testImplementation(Junit.junit)
    testImplementation(Kotlin.coroutines_test)
    androidTestImplementation(Junit.android_junit)
    androidTestImplementation(Junit.espresso_core)

}