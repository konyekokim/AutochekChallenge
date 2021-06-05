package com.konyekokim.cars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konyekokim.cars.CarsViewModel
import com.konyekokim.cars.databinding.ItemPopularCarsBinding
import com.konyekokim.core.network.responses.PopularCarsResponseItem

class PopularCarsAdapter(private val carsViewModel: CarsViewModel)
    :ListAdapter<PopularCarsResponseItem, PopularCarsAdapter.PopularCarsItemViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCarsItemViewHolder {
        val binding = ItemPopularCarsBinding.inflate(LayoutInflater.from(parent.context))
        return PopularCarsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularCarsItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class PopularCarsItemViewHolder(private val binding: ItemPopularCarsBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item: PopularCarsResponseItem) {
            with(binding) {
                popularCar = item
                viewModel = carsViewModel
                executePendingBindings()
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularCarsResponseItem>() {
            override fun areItemsTheSame(oldItem: PopularCarsResponseItem, newItem: PopularCarsResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PopularCarsResponseItem, newItem: PopularCarsResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}