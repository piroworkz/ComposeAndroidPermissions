import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.mavenPublish)
}

android {
    namespace = "com.piroworkz.composeandroidpermissions"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
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
        kotlinCompilerExtensionVersion = "1.5.13"
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
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}

mavenPublishing {
    configure(AndroidSingleVariantLibrary("release", true, true))
    coordinates("com.piroworkz", "compose-android-permissions", "1.0.0")
    pom {
        name.set("ComposeAndroidPermissions")
        description.set("A library to request android permissions in Jetpack Compose")
        url.set("https://github.com/piroworkz/ComposeAndroidPermissions")
        licenses {
            license {
                name = "Apache-2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        developers {
            developer {
                id = "piroworkz"
                name = "David Luna"
                email = "piroworkz@me.com"
            }
        }
        scm {
            connection =
                "scm:git:git://github.com/piroworkz/ComposeAndroidPermissions.git"
            developerConnection =
                "scm:git:ssh://github.com/piroworkz/ComposeAndroidPermissions.git"
            url = "https://github.com/piroworkz/ComposeAndroidPermissions"
        }
    }
    signAllPublications()

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)
}
