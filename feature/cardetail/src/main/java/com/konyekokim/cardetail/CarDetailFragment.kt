package com.konyekokim.cardetail

import android.content.Context
import androidx.fragment.app.Fragment
import com.konyekokim.cardetail.di.CarDetailModule
import com.konyekokim.cardetail.di.DaggerCarDetailComponent
import com.konyekokim.commons.extensions.appContext
import com.konyekokim.core.di.provider.CoreComponentProvider
import javax.inject.Inject

class CarDetailFragment : Fragment(R.layout.fragment_cardetail) {

    @Inject
    lateinit var viewModel: CarDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpDependencyInjection()
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