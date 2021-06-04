package com.konyekokim.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.konyekokim.commons.ui.SingleLiveData
import com.konyekokim.core.data.entities.Car
import javax.inject.Inject

class CarsViewModel @Inject constructor(
    private val pageDataSourceFactory: CarsPageDataSourceFactory
): ViewModel() {

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
}