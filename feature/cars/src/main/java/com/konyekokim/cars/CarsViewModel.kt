package com.konyekokim.cars

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.konyekokim.commons.ui.SingleLiveData
import com.konyekokim.core.data.DataState
import com.konyekokim.core.data.Result
import com.konyekokim.core.data.entities.Car
import com.konyekokim.core.network.responses.PopularCarsResponse
import com.konyekokim.core.network.responses.PopularCarsResponseItem
import com.konyekokim.core.repositories.CarRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CarsViewModel @Inject constructor(
    private val pageDataSourceFactory: CarsPageDataSourceFactory,
    private val carRepository: CarRepository
): ViewModel() {

    private val  _popularCarsData = MutableLiveData<List<PopularCarsResponseItem>>()
    val popularCarsData : LiveData<List<PopularCarsResponseItem>>
        get() = _popularCarsData

    private val _popularCarsState = MutableLiveData<PopularCarsViewState>()
    val popularCarsState : LiveData<PopularCarsViewState>
        get() = _popularCarsState

    fun getPopularCars(){
        _popularCarsState.postValue(PopularCarsViewState(dataState = DataState.Loading))
        viewModelScope.launch {
            when(val response = carRepository.fetchPopularCarBrands()){
                is Result.Success -> {
                    val popularCars = response.data
                    _popularCarsState.postValue(PopularCarsViewState(dataState = DataState.Success))
                    _popularCarsData.postValue(popularCars)
                }
                is Result.Error -> {
                    _popularCarsState.postValue(PopularCarsViewState(dataState = DataState.Error(
                        ERROR_MESSAGE)))
                }
            }
        }
    }

    private val pagedListConfig =
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setEnablePlaceholders(true)
            .setPageSize(20)
            .build()

    private val dataState = Transformations.switchMap(pageDataSourceFactory.sourceLiveData) {
        it.dataState
    }

    internal val data: LiveData<PagedList<Car>> =
        LivePagedListBuilder(pageDataSourceFactory, pagedListConfig).build()

    val state = Transformations.map(dataState) { CarsViewState(dataState = it)}

    private val _event = SingleLiveData<CarsEvent>()
    val event: LiveData<CarsEvent>
        get() = _event

    fun openCarDetail(car: Car) {
        _event.value = CarsEvent.OpenCarDetail(car)
    }

    fun retry() {
        pageDataSourceFactory.retry()
    }

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val ERROR_MESSAGE = "Error occured during fetching the popular car brands"
    }
}