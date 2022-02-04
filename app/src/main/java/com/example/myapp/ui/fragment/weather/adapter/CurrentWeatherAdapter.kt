package com.example.myapp.ui.fragment.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.example.myapp.data.model.currentweather.CurrentWeather
import com.example.myapp.databinding.ItemUsersBinding
import javax.inject.Inject

class CurrentWeatherAdapter @Inject constructor(var glide: RequestManager) :
    ListAdapter<CurrentWeather, CurrentWeatherVH>(DiffCallback()) {

    private var onClickListener: (item: CurrentWeather) -> Unit =
        { item -> }

    fun setOnClickListener(listener: (item: CurrentWeather) -> Unit = { item -> }) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherVH {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentWeatherVH(binding, glide)
    }

    override fun onBindViewHolder(holder: CurrentWeatherVH, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { user ->
            holder.bind(user)
            holder.binding.root.setOnClickListener { buttonView ->
                onClickListener.invoke(currentItem)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CurrentWeather>() {
        override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather) =
            oldItem.id == newItem.id
    }

}