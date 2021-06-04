package com.konyekokim.cardetail.di

import com.konyekokim.cardetail.CarDetailFragment
import com.konyekokim.cardetail.CarDetailViewModel
import com.konyekokim.commons.extensions.viewModel
import com.konyekokim.core.di.scopes.FeatureScope
import com.konyekokim.core.repositories.CarRepository
import dagger.Module
import dagger.Provides

@Module
class CarDetailModule(private val carDetailFragment: CarDetailFragment) {

    @Provides
    @FeatureScope
    fun provideViewModel(
        carRepository: CarRepository
    ) = carDetailFragment.viewModel {
        CarDetailViewModel(carRepository)
    }

}