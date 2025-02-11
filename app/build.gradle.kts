plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.moviequest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moviequest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)
    //dependencia para el splashScreen
    implementation("androidx.core:core-splashscreen:1.0.0-rc01")
    implementation(libs.androidx.runtime.saved.instance.state)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.squareup.retrofit2:retrofit:2.9.0") // o la versión más reciente
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // o la versión más reciente
    implementation("com.squareup.okhttp3:okhttp:4.9.3") // o la versión más reciente
    implementation("com.google.code.gson:gson:2.8.6") // o la versión más reciente

// (Opcional, si usas el interceptor LoggingInterceptor de OkHttp para depuración)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3") // o la versión más reciente

    implementation("com.github.bumptech.glide:glide:4.12.0") // o la versión más reciente
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0") // si es necesario para el procesamiento de anotaciones
}