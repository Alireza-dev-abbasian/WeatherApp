package com.example.myapp.ui.fragment.weather

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.databinding.FragmentCurrentWeatherBinding
import com.example.myapp.ui.base.BaseFragment
import com.example.myapp.ui.fragment.details.BottomSheetDetailsFragment
import com.example.myapp.ui.fragment.weather.adapter.CurrentWeatherAdapter
import com.example.myapp.util.network.Resource
import com.example.myapp.util.ui.hide
import com.example.myapp.util.ui.show
import com.example.myapp.viewmodel.CurrentWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherFragment : BaseFragment<FragmentCurrentWeatherBinding>() {

    private val viewModel: CurrentWeatherViewModel by viewModels()

    @Inject
    lateinit var weatherAdapter: CurrentWeatherAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FragmentCurrentWeatherBinding =
        FragmentCurrentWeatherBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupRecyclerview()
        onClickView()
        onClickAdapter()
        observeData()
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
        binding.toolbar.apply {
            (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
            (activity as AppCompatActivity?)!!.supportActionBar?.title = ""

            title.apply {
                text = "Weather"
                setTextColor(Color.WHITE)
            }
            back.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun setupRecyclerview() {
        binding.apply {

            recyclerview.apply {
                adapter = weatherAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun onClickView() {
        binding.apply {

            fabAddUser.setOnClickListener { view ->
                viewModel.locationProvider.getLocation()
                viewModel.locationProvider.observe(viewLifecycleOwner, { data ->
                    data?.let {
                        viewModel.getCurrentWeather(it.lat.toString(), it.lon.toString())
                    }
                })
            }
        }
    }

    private fun onClickAdapter() {
        weatherAdapter.apply {
            setOnClickListener {
                BottomSheetDetailsFragment.newInstance(it)
                    .show(childFragmentManager, "")
            }
        }

    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.currentWeatherState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loading.apply {
                            show()
                            playAnimation()
                        }
                        delay(2000)
                    }
                    is Resource.Success -> {
                        resource.data?.let {
                            binding.loading.apply {
                                cancelAnimation()
                                hide()
                            }
                            weatherAdapter.submitList(mutableListOf(it))
                            binding.recyclerview.scrollToPosition(weatherAdapter.currentList.size - 1)
                        }
                    }
                    is Resource.Error -> {
                        binding.loading.hide()
                    }
                }
            }
        }
    }
}