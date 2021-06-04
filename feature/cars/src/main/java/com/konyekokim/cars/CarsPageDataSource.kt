package com.konyekokim.cars

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PACKAGE_PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.konyekokim.core.data.DataState
import com.konyekokim.core.data.Result
import com.konyekokim.core.data.entities.Car
import com.konyekokim.core.repositories.CarRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

open class CarsPageDataSource @Inject constructor(
    private val carRepository: CarRepository,
    private val scope: CoroutineScope
): PageKeyedDataSource<Int, Car>(){

    @VisibleForTesting(otherwise = PACKAGE_PRIVATE)
    internal val dataState = MutableLiveData<DataState>()
    private var retry: (() -> Unit)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Car>
    ) {
        dataState.postValue(DataState.Loading)
        scope.launch {
            when(val response = carRepository.getAllCars(1)) {
                is Result.Success -> {
                    val cars = response.data
                    callback.onResult(cars, null, 2)
                    dataState.postValue(DataState.Success)
                }
                is Result.Error -> {
                    retry =  {loadInitial(params, callback)}
                    dataState.postValue(DataState.Error(response.message))
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Car>) =
        Unit

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Car>) {
        val page = params.key
        dataState.postValue(DataState.Loading)
        scope.launch {
            when (val response = carRepository.getAllCars(page)) {
                is Result.Success -> {
                    val cars = response.data
                    callback.onResult(cars, page + 1)
                    dataState.postValue(DataState.Success)
                }
                is Result.Error -> {
                    retry = {loadAfter(params, callback)}
                    dataState.postValue(DataState.Error(response.message))
                }
            }
        }
    }

    @VisibleForTesting(otherwise = PACKAGE_PRIVATE)
    fun retry(){
        retry?.invoke()
    }
}