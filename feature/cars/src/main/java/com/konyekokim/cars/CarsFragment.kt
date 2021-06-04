package com.konyekokim.cars

import android.content.Context
import androidx.fragment.app.Fragment
import com.konyekokim.cars.di.CarsModule
import com.konyekokim.cars.di.DaggerCarsComponent
import com.konyekokim.commons.extensions.appContext
import com.konyekokim.core.di.provider.CoreComponentProvider
import javax.inject.Inject

class CarsFragment: Fragment(R.layout.fragment_cars) {

    @Inject
    lateinit var viewModel: CarsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpDependencyInjection()
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