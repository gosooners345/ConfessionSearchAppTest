plugins {
    id("com.android.application")
    id("kotlin-android")

}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.confessionsearchapptest.release1"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
javaCompileOptions {
    annotationProcessorOptions{
        arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
    }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true

    }

}


dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.6.0-alpha01")
    implementation( "androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("com.github.nitrico.lastadapter:lastadapter:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("com.readystatesoftware.sqliteasset:sqliteassethelper:2.0.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("com.github.gabriel-TheCode:AestheticDialogs:1.3.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("com.github.chnouman:AwesomeDialog:1.0.5")
    implementation ("androidx.coordinatorlayout:coordinatorlayout:1.1.0")
    implementation("com.github.vedraj360:DesignerToast:0.1.3")
    implementation("androidx.room:room-common:2.4.0-rc01")
    implementation("androidx.room:room-runtime:2.4.0-rc01")
    implementation ("androidx.preference:preference-ktx:1.1.1")
    implementation ("com.github.chivorns:smartmaterialspinner:1.5.0")
    implementation("com.github.Spikeysanju:MotionToast:1.3.3.4")
    implementation("androidx.sqlite:sqlite-ktx:2.1.0")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta01")
    implementation("com.mikepenz:fastadapter:5.6.0")
    implementation("com.mikepenz:fastadapter-extensions-binding:5.6.0") // view binding helpers
    implementation("com.mikepenz:fastadapter-extensions-diff:5.6.0") // diff util helpers
    implementation("com.mikepenz:fastadapter-extensions-drag:5.6.0") // drag support
    implementation("com.mikepenz:fastadapter-extensions-paged:5.6.0") // paging support
    implementation ("com.mikepenz:fastadapter-extensions-scroll:5.6.0") // scroll helpers
    implementation( "com.mikepenz:fastadapter-extensions-swipe:5.6.0") // swipe support
    implementation( "com.mikepenz:fastadapter-extensions-ui:5.6.0")// pre-defined ui components
    implementation( "com.mikepenz:fastadapter-extensions-utils:5.6.0") // needs the `expandable`, `drag` and `scroll` extension.
    implementation("io.github.medyo:android-about-page:2.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    annotationProcessor ("android.arch.persistence.room:compiler:1.1.1")
    annotationProcessor("androidx.room:room-compiler:2.4.0-rc01")
}
