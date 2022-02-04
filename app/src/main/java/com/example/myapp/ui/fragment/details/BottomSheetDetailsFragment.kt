package com.example.myapp.ui.fragment.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestManager
import com.example.myapp.R
import com.example.myapp.data.model.currentweather.CurrentWeather
import com.example.myapp.databinding.FragmentBottomSheetDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetDetailsFragment() : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetDetailsBinding

    @Inject
    lateinit var glide: RequestManager

    companion object {
        var currentWeather: CurrentWeather? = null
        fun newInstance(data: CurrentWeather): BottomSheetDetailsFragment {
            val args = Bundle()
            currentWeather = data
            val fragment = BottomSheetDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetDetailsBinding.inflate(inflater, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        initView()
        setupBottomSheet(view)
        onClickView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupActionBar() {
        setHasOptionsMenu(true)
        binding.apply {
            (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar.toolbar)
            (activity as AppCompatActivity?)!!.supportActionBar?.title = ""
            toolbar.apply {
                currentWeather?.let {
                    val icon: String = it.weather[0].icon
                    val iconUrl = "http://openweathermap.org/img/w/$icon.png"
                    image1.apply {
                        visibility = View.VISIBLE
                        glide.load(iconUrl)
                            .placeholder(
                                ContextCompat.getDrawable(
                                    this.context, R.drawable.progress_animation
                                )
                            ).circleCrop().into(this)
                    }

                    title.text = it.name
                    title.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
        }
    }

    fun initView() {
        binding.apply {
            currentWeather?.let {
                weather.text = "weather: ${it.weather[0].main}"
                desc.text = "description: ${it.weather[0].description}"
                country.text = "country: ${it.sys.country}"
                city.text = "city: ${it.name}"
            }
        }
    }

    fun setupBottomSheet(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            view.viewTreeObserver.removeOnGlobalLayoutListener {}
            val bottomSheetDialog: BottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById(R.id.design_bottom_sheet) as? FrameLayout
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = 0
                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    }

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            dismiss()
                        }
                    }
                })
            }
        }
    }

    private fun onClickView() {
        binding.apply {
            toolbar.back.setOnClickListener {
                dismiss()
            }

        }
    }

}