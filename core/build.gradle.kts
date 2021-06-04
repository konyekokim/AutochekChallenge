
plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
}

android {
    buildTypes.forEach {
        it.buildConfigField(
            type = "String",
            name = "API_BASE_URL", value = "\"https://api-prod.autochek.africa/v1/\""
        )
        it.buildConfigField(type = "String", name = "CAR_DATABASE_NAME", value = "\"cars-db\"")
        it.buildConfigField(type = "int", name = "CAR_DATABASE_VERSION", value = "1")
    }
}

dependencies {

    implementation(Dependencies.Kotlin.stdlib)

    implementation(Dependencies.Kotlin.Coroutines.core)

    //AndroidX
    implementation(Dependencies.AndroidX.Room.core)
    kapt(Dependencies.AndroidX.Room.compiler)
    implementation(Dependencies.AndroidX.Room.extensions)

    //Dagger
    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.compiler)

    //Retrofit-OkHttp
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.OkHttp.okhttp)
    implementation(Dependencies.OkHttp.loggingInterceptor)

}