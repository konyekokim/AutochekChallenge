package com.konyekokim.cars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.konyekokim.cars.CarsViewModel
import com.konyekokim.cars.databinding.ItemCarBinding
import com.konyekokim.commons.extensions.convertToNairaDisplay
import com.konyekokim.core.data.entities.Car

class CarsAdapter(
    private val carsViewModel: CarsViewModel
) : PagedListAdapter<Car, CarsAdapter.CarItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarItemViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context))
        return CarItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class CarItemViewHolder(private val binding: ItemCarBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Car) {
            with(binding) {
                car = item
                viewModel = carsViewModel
                executePendingBindings()
            }
            binding.carPrice.text = item.marketplacePrice?.convertToNairaDisplay()
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Car>() {
            override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem == newItem
            }
        }
    }
}