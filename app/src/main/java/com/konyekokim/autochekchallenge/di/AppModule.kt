package com.konyekokim.autochekchallenge.di

import android.content.Context
import com.konyekokim.autochekchallenge.AutochekApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: AutochekApplication): Context =
        application.applicationContext

}