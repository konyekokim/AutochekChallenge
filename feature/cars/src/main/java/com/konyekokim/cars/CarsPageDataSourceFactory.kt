package com.konyekokim.cars

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.konyekokim.core.data.entities.Car
import javax.inject.Inject
import javax.inject.Provider

class CarsPageDataSourceFactory @Inject constructor(
    private val carsPageDataSourceProvider: Provider<CarsPageDataSource>
): DataSource.Factory<Int, Car>() {

    val sourceLiveData = MutableLiveData<CarsPageDataSource>()

    override fun create(): DataSource<Int, Car> {
        val carsDataSource = carsPageDataSourceProvider.get()
        sourceLiveData.postValue(carsDataSource)
        return carsDataSource
    }

    fun retry(){
        sourceLiveData.value?.retry()
    }
}