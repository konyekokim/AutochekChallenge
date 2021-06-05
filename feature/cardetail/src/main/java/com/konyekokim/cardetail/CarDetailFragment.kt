package com.konyekokim.cardetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.konyekokim.cardetail.adapter.CarMediaAdapter
import com.konyekokim.cardetail.databinding.FragmentCardetailBinding
import com.konyekokim.cardetail.di.CarDetailModule
import com.konyekokim.cardetail.di.DaggerCarDetailComponent
import com.konyekokim.commons.extensions.*
import com.konyekokim.core.data.DataState
import com.konyekokim.core.di.provider.CoreComponentProvider
import com.konyekokim.core.network.responses.CarDetailResponse
import com.konyekokim.core.network.responses.CarMediaListItem
import java.math.RoundingMode
import javax.inject.Inject

class CarDetailFragment : Fragment(R.layout.fragment_cardetail) {

    @Inject
    lateinit var viewModel: CarDetailViewModel

    private lateinit var binding: FragmentCardetailBinding

    private lateinit var carMediaAdapter: CarMediaAdapter

    private val args: CarDetailFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpDependencyInjection()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCardetailBinding.bind(view)
        setUpCarMediaRecyclerView()
        initViews()
        observe(viewModel.carMediaListState, ::onCarMediaViewStateChanged)
        observe(viewModel.carMediaListData, ::onCarMediaViewDataChanged)
        observe(viewModel.carDetailState, ::onCarDetailViewStateChanged)
        observe(viewModel.carDetailData, ::onCarDetailViewDataChanged)
        viewModel.fetchCarDetail(args.carId)
        viewModel.fetchCarMediaList(args.carId)
    }

    private fun initViews(){
        binding.carNameText.text = args.carName
        binding.backArrowImg.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setUpCarMediaRecyclerView(){
        carMediaAdapter = CarMediaAdapter(viewModel)
        with(binding.carMediaList){
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = carMediaAdapter
        }
    }

    private fun onCarMediaViewStateChanged(carMediaListViewState: CarMediaListViewState){
        when(val dataState = carMediaListViewState.dataState){
            is DataState.Error -> {
                showSnackbar(dataState.message)
            }
            else -> {
                //do nothing
            }
        }
    }

    private fun onCarMediaViewDataChanged(carMediaList: List<CarMediaListItem>){
        carMediaAdapter.submitList(carMediaList)
    }

    private fun onCarDetailViewStateChanged(carDetailViewState: CarDetailViewState){
        when(val dataState = carDetailViewState.dataState){
            is DataState.Error -> {
                showSnackbar(dataState.message)
            }
            else -> {
                //do nothing
            }
        }
    }

    private fun onCarDetailViewDataChanged(carDetailResponse: CarDetailResponse){
        binding.carMediaImage.loadImage(carDetailResponse.imageUrl)
        binding.model.text = ("${carDetailResponse.model.make.name} ${carDetailResponse.model.name}")
        binding.rating.text = carDetailResponse.gradeScore.to1dpString()
        binding.interiorColor.text = carDetailResponse.interiorColor
        binding.exteriorColor.text = carDetailResponse.exteriorColor
        binding.engineType.text = carDetailResponse.engineType
        binding.year.text = carDetailResponse.year.toString()
        binding.mileage.text = ("${carDetailResponse.mileage} ${carDetailResponse.mileageUnit}")
        binding.city.text = carDetailResponse.city
        binding.reason.text = carDetailResponse.reasonForSelling
        binding.sellingCondition.text = carDetailResponse.sellingCondition
        binding.carPrice.text = carDetailResponse.marketplacePrice.toString()
    }

    private fun setUpDependencyInjection(){
        DaggerCarDetailComponent
            .builder()
            .coreComponent((appContext as CoreComponentProvider).provideCoreComponent())
            .carDetailModule(CarDetailModule(this))
            .build()
            .inject(this)

    }
}