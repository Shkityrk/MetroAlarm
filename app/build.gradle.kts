plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.samsungschoolproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.samsungschoolproject"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // UI Components
    implementation("androidx.appcompat:appcompat:1.6.1") // Support for ActionBar, themed Activities, and compatibility with older Android versions
    implementation("com.google.android.material:material:1.11.0") // Material Design components like buttons, cards, etc.
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Layout for creating flexible and responsive UI
    implementation("androidx.recyclerview:recyclerview:1.3.0") // Support for RecyclerView, a flexible view for providing a limited window into large data sets

    // Legacy Support
    implementation("androidx.legacy:legacy-support-v4:1.0.0") // Support library for older Android versions

    // Activity
    implementation("androidx.activity:activity:1.8.0") // Support for Activity class and component lifecycle

    // Testing
    testImplementation("junit:junit:4.13.2") // JUnit testing framework
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // JUnit extensions for Android testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Espresso for UI testing

    // Room Database
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion") // Room runtime
    annotationProcessor("androidx.room:room-compiler:$roomVersion") // Room annotation processor for code generation

    // SQLite
    implementation("org.xerial:sqlite-jdbc:3.45.2.0") // SQLite JDBC driver

    // Google Play Services
    implementation("com.google.android.gms:play-services-location:18.0.0") // Google Play services for location

    // JSON Parsing
    implementation("com.google.code.gson:gson:2.8.7") // Gson library for JSON parsing


    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

}
