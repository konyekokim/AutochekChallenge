package com.konyekokim.cardetail.di

import com.konyekokim.cardetail.CarDetailFragment
import com.konyekokim.core.di.CoreComponent
import com.konyekokim.core.di.scopes.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [CarDetailModule::class],
    dependencies = [CoreComponent::class]
)
interface CarDetailComponent {
    fun inject(carDetailFragment: CarDetailFragment)
}