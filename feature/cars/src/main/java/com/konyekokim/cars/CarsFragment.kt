package com.konyekokim.cars

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.konyekokim.cars.adapter.CarsAdapter
import com.konyekokim.cars.adapter.PopularCarsAdapter
import com.konyekokim.cars.databinding.FragmentCarsBinding
import com.konyekokim.cars.di.CarsModule
import com.konyekokim.cars.di.DaggerCarsComponent
import com.konyekokim.commons.extensions.appContext
import com.konyekokim.commons.extensions.observe
import com.konyekokim.commons.extensions.showSnackbar
import com.konyekokim.core.data.DataState
import com.konyekokim.core.data.entities.Car
import com.konyekokim.core.di.provider.CoreComponentProvider
import com.konyekokim.core.network.responses.PopularCarsResponseItem
import javax.inject.Inject

class CarsFragment: Fragment(R.layout.fragment_cars) {

    @Inject
    lateinit var viewModel: CarsViewModel

    private lateinit var binding: FragmentCarsBinding

    private lateinit var popularCarsAdapter: PopularCarsAdapter
    private lateinit var carsAdapter: CarsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpDependencyInjection()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarsBinding.bind(view)
        setUpPopularCarsRecyclerview()
        setUpCarsRecyclerView()
        observe(viewModel.state, ::onCarsViewStateChanged)
        observe(viewModel.popularCarsState, ::onPopularCarsViewStateChanged)
        observe(viewModel.data, ::onCarsViewDataChanged)
        observe(viewModel.popularCarsData, ::onPopularCarsViewDataChanged)
        observe(viewModel.event, ::onViewEvent)
    }

    private fun setUpPopularCarsRecyclerview(){
        popularCarsAdapter = PopularCarsAdapter(viewModel)
        with(binding.popularCarsList) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularCarsAdapter
        }
    }

    private fun setUpCarsRecyclerView(){
        carsAdapter = CarsAdapter(viewModel)
        with(binding.carsList) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = carsAdapter
        }
    }

    private fun onViewEvent(viewEvent: CarsEvent){
        when (viewEvent) {
            is CarsEvent.OpenCarDetail -> {
                findNavController().navigate(
                    CarsFragmentDirections.actionCarsFragmentToCarDetailFragment(
                        viewEvent.carId, viewEvent.carName
                    )
                )
            }
        }
    }

    private fun onCarsViewStateChanged(carsViewState: CarsViewState){
        when(val dataState = carsViewState.dataState){
            is DataState.Error -> {
                showSnackbar(dataState.message)
            }
            else -> {
                //do nothing
            }
        }
    }

    private fun onPopularCarsViewStateChanged(popularCarsViewState: PopularCarsViewState) {
        when(val dataState = popularCarsViewState.dataState){
            is DataState.Error -> {
                showSnackbar(dataState.message)
            }
            else -> {
                //do nothing
            }
        }
    }

    private fun onCarsViewDataChanged(cars: PagedList<Car>){
        carsAdapter.submitList(cars)
    }

    private fun onPopularCarsViewDataChanged(popularCars: List<PopularCarsResponseItem>){
        popularCarsAdapter.submitList(popularCars)
    }

    private fun setUpDependencyInjection() {
        DaggerCarsComponent
            .builder()
            .coreComponent((appContext as CoreComponentProvider).provideCoreComponent())
            .carsModule(CarsModule(this))
            .build()
            .inject(this)
    }
}