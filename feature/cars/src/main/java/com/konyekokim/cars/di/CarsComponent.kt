package com.konyekokim.cars.di

import com.konyekokim.core.di.CoreComponent
import com.konyekokim.core.di.scopes.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [CarsModule::class],
    dependencies = [CoreComponent::class]
)
interface CarsComponent {
}