package com.konyekokim.autochekchallenge.di

import com.konyekokim.autochekchallenge.AutochekApplication
import com.konyekokim.core.di.CoreComponent
import com.konyekokim.core.di.scopes.AppScope
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {
    fun inject(application: AutochekApplication)
}