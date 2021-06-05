package com.konyekokim.cars

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.konyekokim.core.data.DataState
import com.konyekokim.testcommons.rules.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CarsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var dataSourceFactory: CarsPageDataSourceFactory

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<CarsViewState>

    @MockK(relaxed = true)
    lateinit var eventObserver: Observer<CarsEvent>

    lateinit var viewModel: CarsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `state is success when data state is success`() {
        val dataState = DataState.Success
        val fakePageDataSource = FakeCarsPageDataSource(dataState)
        val fakeSourceLiveData = MutableLiveData<CarsPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = CarsViewModel(pageDataSourceFactory = dataSourceFactory, carRepository = mockk())
        viewModel.state.observeForever(stateObserver)

        val expectedState = CarsViewState(dataState = dataState)
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun `state is error when data state is error`() {
        val dataState = DataState.Error(message = "")
        val fakePageDataSource = FakeCarsPageDataSource(dataState)
        val fakeSourceLiveData = MutableLiveData<CarsPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = CarsViewModel(pageDataSourceFactory = dataSourceFactory, carRepository = mockk())
        viewModel.state.observeForever(stateObserver)

        val expectedState = CarsViewState(dataState = dataState)
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun `state is loading when data state is loading`() {
        val dataState = DataState.Loading
        val fakePageDataSource = FakeCarsPageDataSource(dataState)
        val fakeSourceLiveData = MutableLiveData<CarsPageDataSource>(fakePageDataSource)
        every {
            dataSourceFactory.sourceLiveData
        } returns fakeSourceLiveData

        viewModel = CarsViewModel(pageDataSourceFactory = dataSourceFactory, carRepository = mockk())
        viewModel.state.observeForever(stateObserver)

        val expectedState = CarsViewState(dataState = dataState)
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun `retry is invoked properly`() {
        viewModel = CarsViewModel(pageDataSourceFactory = dataSourceFactory, carRepository = mockk())
        viewModel.retry()

        verify { dataSourceFactory.retry() }
    }

    @Test
    fun `open detail is invoked properly`() {
        viewModel = CarsViewModel(pageDataSourceFactory = dataSourceFactory, carRepository = mockk())
        viewModel.event.observeForever(eventObserver)

        val carId = ""
        val carTitle = "Car-123"
        viewModel.openCarDetail(carId, carTitle)

        val event = CarsEvent.OpenCarDetail(carId, carTitle)
        assertEquals(event, viewModel.event.value)
        verify { eventObserver.onChanged(event) }
    }

    inner class FakeCarsPageDataSource(
        dataState: DataState
    ): CarsPageDataSource(
        carRepository = mockk(),
        scope = mockk()
    ) {
        init {
            this.dataState.postValue(dataState)
        }
    }

}