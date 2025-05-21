buildscript {
    val agp_version by extra("8.9.2")

}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.9.2" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.9.0" apply false
}