package com.konyekokim.cars.di

import androidx.lifecycle.viewModelScope
import com.konyekokim.cars.CarsFragment
import com.konyekokim.cars.CarsPageDataSource
import com.konyekokim.cars.CarsPageDataSourceFactory
import com.konyekokim.cars.CarsViewModel
import com.konyekokim.commons.extensions.viewModel
import com.konyekokim.core.di.scopes.FeatureScope
import com.konyekokim.core.repositories.CarRepository
import dagger.Module
import dagger.Provides

@Module
class CarsModule(
    private val carsFragment: CarsFragment
) {

    @Provides
    @FeatureScope
    fun provideViewModel(
        pageDataSourceFactory: CarsPageDataSourceFactory,
        carRepository: CarRepository
    ) = carsFragment.viewModel {
        CarsViewModel(pageDataSourceFactory, carRepository)
    }

    @Provides
    fun provideCarDataSource(
        carRepository: CarRepository,
        viewModel: CarsViewModel
    ) = CarsPageDataSource(carRepository, viewModel.viewModelScope)

}