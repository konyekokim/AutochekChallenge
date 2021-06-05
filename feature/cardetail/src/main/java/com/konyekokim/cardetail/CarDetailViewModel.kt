package com.konyekokim.cardetail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konyekokim.commons.ui.SingleLiveData
import com.konyekokim.core.data.DataState
import com.konyekokim.core.data.Result
import com.konyekokim.core.network.responses.CarDetailResponse
import com.konyekokim.core.network.responses.CarMediaListItem
import com.konyekokim.core.repositories.CarRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CarDetailViewModel @Inject constructor(
    private val carRepository: CarRepository
): ViewModel() {

    private val _carDetailData = MutableLiveData<CarDetailResponse>()
    val carDetailData : LiveData<CarDetailResponse>
        get() = _carDetailData

    private val _carMediaListData = MutableLiveData<List<CarMediaListItem>>()
    val carMediaListData : LiveData<List<CarMediaListItem>>
        get() = _carMediaListData

    private val _carDetailState = MutableLiveData<CarDetailViewState>()
    val carDetailState : LiveData<CarDetailViewState>
        get() = _carDetailState

    private val _carMediaListState = MutableLiveData<CarMediaListViewState>()
    val carMediaListState : LiveData<CarMediaListViewState>
        get() = _carMediaListState

    fun fetchCarDetail(carId: String){
        _carDetailState.postValue(CarDetailViewState(dataState = DataState.Loading))
        viewModelScope.launch {
            when(val response = carRepository.getCarDetails(carId)){
                is Result.Success -> {
                    _carDetailState.postValue(CarDetailViewState(dataState = DataState.Success))
                    _carDetailData.postValue(response.data)
                }
                is Result.Error -> {
                    _carDetailState.postValue(CarDetailViewState(dataState = DataState.Error(
                        ERROR_MESSAGE)))
                }
            }
        }
    }

    fun fetchCarMediaList(carId: String){
        _carMediaListState.postValue(CarMediaListViewState(dataState = DataState.Loading))
        viewModelScope.launch {
            when(val response = carRepository.getCarMedialList(carId)){
                is Result.Success -> {
                    _carMediaListState.postValue(CarMediaListViewState(dataState = DataState.Success))
                    _carMediaListData.postValue(response.data)
                }
                is Result.Error -> {
                    _carMediaListState.postValue(CarMediaListViewState(dataState = DataState.Error(
                        ERROR_MESSAGE)))
                }
            }
        }
    }

    private val _onMediaItemClickedEvent = SingleLiveData<CarDetailEvent>()
    val onMediaItemClickedEvent : LiveData<CarDetailEvent>
        get() = _onMediaItemClickedEvent

    fun onMediaItemClicked(carImageUrl: String){
        _onMediaItemClickedEvent.value = CarDetailEvent.OnMediaItemClicked(carImageUrl)
    }

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val ERROR_MESSAGE = "Error occured during fetching the popular car brands"
    }
}