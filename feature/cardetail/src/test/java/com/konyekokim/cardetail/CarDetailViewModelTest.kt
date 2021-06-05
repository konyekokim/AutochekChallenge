package com.konyekokim.cardetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.konyekokim.core.data.DataState
import com.konyekokim.core.data.Result
import com.konyekokim.core.data.entities.Car
import com.konyekokim.core.network.responses.CarDetailResponse
import com.konyekokim.core.network.responses.CarMake
import com.konyekokim.core.network.responses.CarModelDetail
import com.konyekokim.core.repositories.CarRepository
import com.konyekokim.testcommons.rules.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarDetailViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var repository: CarRepository

    lateinit var viewModel: CarDetailViewModel

    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<CarDetailViewState>

    @MockK(relaxed = true)
    lateinit var dataObserver: Observer<CarDetailResponse>

    val carId = "test_car"
    val car =
        CarDetailResponse(
            id = "stuff",
            year = 200,
            mileage  = 300,
            mileageUnit = "km",
            marketplacePrice = 20000000,
            interiorColor = "yellow",
            exteriorColor = "black",
            engineType  = "V6",
            city = "Lagos",
            imageUrl = "stufff.jpg",
            reasonForSelling = "profits",
            sellingCondition = "foreign",
            model =  CarModelDetail(
                make = CarMake(
                    name = "Konye",
                    imageUrl = "string"
                ),
                name = "kokim",
                series = "Pro max"
            ),
            transmission = "automatic",
            gradeScore = 4.9
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CarDetailViewModel(repository)
    }

    @Test
    fun `state order is proper when car detail data state is success`() =
        coroutineTestRule.dispatcher.runBlockingTest {
            coEvery {
                repository.getCarDetails(carId)
            } returns Result.Success(car)

            viewModel.carDetailState.observeForever(stateObserver)

            viewModel.fetchCarDetail(carId)

            verifyOrder {
                stateObserver.onChanged(CarDetailViewState(DataState.Loading))
                stateObserver.onChanged(CarDetailViewState(DataState.Success))
            }
        }

    @Test
    fun `fetch Car detail properly`() = coroutineTestRule.dispatcher.runBlockingTest {
        coEvery {
            repository.getCarDetails(carId)
        } returns Result.Success(car)

        viewModel.carDetailData.observeForever(dataObserver)

        viewModel.fetchCarDetail(carId)

        verifyOrder {
            dataObserver.onChanged(car)
        }
    }

    @Test
    fun `state is error when there is no Car`() = coroutineTestRule.dispatcher.runBlockingTest {
        coEvery {
            repository.getCarDetails(carId)
        } returns Result.Error(CarDetailViewModel.ERROR_MESSAGE)

        viewModel.carDetailState.observeForever(stateObserver)

        viewModel.fetchCarDetail(carId)

        verifyOrder {
            stateObserver.onChanged(CarDetailViewState(DataState.Loading))
            stateObserver.onChanged(
                CarDetailViewState(DataState.Error(CarDetailViewModel.ERROR_MESSAGE)))
        }
    }

}