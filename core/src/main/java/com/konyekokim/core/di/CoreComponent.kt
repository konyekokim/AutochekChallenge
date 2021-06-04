package com.konyekokim.core.di

import android.content.Context
import com.konyekokim.core.data.AppCoroutineDispatchers
import com.konyekokim.core.database.CarDao
import com.konyekokim.core.di.modules.ContextModule
import com.konyekokim.core.di.modules.DatabaseModule
import com.konyekokim.core.di.modules.DispatcherModule
import com.konyekokim.core.di.modules.NetworkModule
import com.konyekokim.core.network.services.CarService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DispatcherModule::class
    ]
)
interface CoreComponent {
    fun context(): Context
    fun movieService(): CarService
    fun movieDao(): CarDao
    fun coroutineDispatchers(): AppCoroutineDispatchers
}