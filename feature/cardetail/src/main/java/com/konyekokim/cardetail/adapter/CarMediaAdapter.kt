package com.konyekokim.cardetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konyekokim.cardetail.CarDetailViewModel
import com.konyekokim.cardetail.databinding.ItemCarMediaBinding
import com.konyekokim.core.network.responses.CarMediaListItem

class CarMediaAdapter(private val carDetailViewModel: CarDetailViewModel)
    :ListAdapter<CarMediaListItem, CarMediaAdapter.CarMediaItemViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarMediaItemViewHolder {
        val binding = ItemCarMediaBinding.inflate(LayoutInflater.from(parent.context))
        return CarMediaItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarMediaItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class CarMediaItemViewHolder(private val binding: ItemCarMediaBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CarMediaListItem) {
            with(binding){
                carMediaItem = item
                viewModel = viewModel
                executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CarMediaListItem>() {
            override fun areItemsTheSame(oldItem: CarMediaListItem, newItem: CarMediaListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CarMediaListItem, newItem: CarMediaListItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}