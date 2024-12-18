plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.chaquo.python)
}

android {
    namespace = "com.botrista.countertopbot"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.botrista.countertopbot"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        ndk {
            abiFilters += listOf("arm64-v8a")
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
//    sourceSets{
//        val utilsSrc = ".${project.projectDir}/src/main/python/drinkbot-firmware-interface"
//        getByName("main"){
//            kotlin.srcDir("src/main/python/drinkbot-firmware-interface")
//        }
//    }

}

chaquopy {

    defaultConfig {
        version = "3.11"
        pip {
            install("black==23.10.1")
            install("isort==5.12.0")
            install("pre-commit==3.5.0")
            install("pymodbus==3.5.4")
            install("pyserial==3.5")
            install("pytest==8.1.1")
            install("pytest-cov==5.0.0")

            install("pyzmq==25.1.1")
            options("--find-links", "${project.projectDir}/src/main/python/libs/pyzmq")
        }
    }
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.bitcoinj.core)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.spongy.castle.core)
    implementation(libs.spongy.castle.prov)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.robolectric)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
}