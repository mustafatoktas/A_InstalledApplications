// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    //kotlin 2.0.0 için gerekli
    alias(libs.plugins.compose.compiler) apply false

    //hilt
    alias(libs.plugins.hilt) apply false

    //ksp
    alias(libs.plugins.ksp) apply false
}