package com.egyptfwd.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egyptfwd.asteroidradar.database.AsteroidEntity
import com.egyptfwd.asteroidradar.databinding.RowItemAsteroidBinding

class MainRVAdapter(private val onClickListener: AsteroidListener) :
    ListAdapter<AsteroidEntity, MainRVAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: RowItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: AsteroidListener, asteroid: AsteroidEntity) {
            binding.asteroid = asteroid
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<AsteroidEntity>() {

        override fun areItemsTheSame(
            oldItem: AsteroidEntity,
            newItem: AsteroidEntity
        )
                : Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AsteroidEntity,
            newItem: AsteroidEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    class AsteroidListener(val clickListener: (asteroid: AsteroidEntity) -> Unit) {

        fun onClick(asteroid: AsteroidEntity) = clickListener(asteroid)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val asteroidItem = getItem(position)
        holder.bind(onClickListener, asteroidItem)

    }

}