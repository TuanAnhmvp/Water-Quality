package com.example.hauiwaterquality.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.data.response.DataResponse
import com.example.hauiwaterquality.data.response.LoadingStatus
import com.example.hauiwaterquality.databinding.FragmentHomeBinding
import com.example.hauiwaterquality.ui.base.AbsBaseFragment
import com.example.hauiwaterquality.utils.CheckInternet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : AbsBaseFragment<FragmentHomeBinding>() {
    private val mViewModel: HomeViewModel by viewModels()

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        binding.viewModel = mViewModel
        mViewModel.checkInternet(requireContext())
        mViewModel.getData()

        observer()

        lifecycleScope.launch(Dispatchers.Default) {
            while (true) {
                delay(3000)
                mViewModel.checkInternet(requireContext())
                mViewModel.getData()
            }
        }

        binding.card1.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(1))
        }

        binding.card2.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(2))
        }

        binding.card3.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(3))
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.turnOff -> {
                    showTurnOffDialog()
                    true
                } else -> {
                    false
                }
            }
        }

    }

    private fun observer() {
        mViewModel.dataResponseLiveData.observe(this) {
            it?.let {
                /*if (it.loadingStatus == LoadingStatus.Loading) {
                    binding.progressLoading.visibility = View.VISIBLE
                    binding.layoutRoot.visibility = View.GONE
                } else {
                    binding.progressLoading.visibility = View.GONE
                    binding.layoutRoot.visibility = View.VISIBLE
                }*/
                if (it.loadingStatus == LoadingStatus.Success) {
                    val body = (it as DataResponse.DataSuccess).body
                    val temperature = body.temperature
                    when {
                        temperature in 20.0..30.0 -> {
                            binding.tvWarningTemp.text = "Nhi???t ????? t???t"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                        temperature < 18 -> {
                            binding.tvWarningTemp.text = "C???nh b??o nhi???t ????? th???p"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_good))
                        }
                        temperature > 33 -> {
                            binding.tvWarningTemp.text = "C???nh b??o nhi???t ????? cao"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_good))
                        }
                        else -> {
                            binding.tvWarningTemp.text = "Nhi???t ????? b??nh th?????ng"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                    }

                    binding.tvTemperature.text = "${body.temperature}???"
                    //binding.tvTimeTemperature.text = body.timestamps.toString()
                    binding.tvTimeTemperature.text = epochToIso8601(1678336226)
                    //binding.tvTimeTemperature.text = epochToIso8601(body.timestamps)

                }

            }
        }

    }

    private fun epochToIso8601(time: Long): String {
        val format = "dd MMM yyyy - HH:mm:ss" // you can add the format you need
        val sdf = SimpleDateFormat(format, Locale.getDefault()) // default local
        sdf.timeZone = TimeZone.getDefault() // set anytime zone you need
        return sdf.format(Date(time * 1000))
    }

    private fun showTurnOffDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dong_ung_dung))
            .setMessage(resources.getString(R.string.message_turn_off_app))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
            }
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                // Respond to positive button press
            requireActivity().finish()
            }
            .show()
    }





}