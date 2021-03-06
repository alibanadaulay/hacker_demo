// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.0.0" apply false
    id("com.android.library") version "7.0.0"
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

android {
    compileSdk = 31
}

buildscript {
    dependencies {
        classpath(Hilt.gradle)
    }
}