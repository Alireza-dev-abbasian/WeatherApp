package com.example.myapp.ui.fragment.weather.adapter

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.myapp.R
import com.example.myapp.data.model.currentweather.CurrentWeather
import com.example.myapp.databinding.ItemUsersBinding

class CurrentWeatherVH(val binding: ItemUsersBinding, val glide: RequestManager) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: CurrentWeather) {
        binding.apply {
            name.text = "${item.weather[0].main} ${item.weather[0].description}"
            val icon: String = item.weather[0].icon
            val iconUrl = "http://openweathermap.org/img/w/$icon.png"
            glide.load(iconUrl)
                .placeholder(
                    ContextCompat.getDrawable(
                        this.image.context, R.drawable.progress_animation
                    )
                ).circleCrop().into(image)
        }
    }

}