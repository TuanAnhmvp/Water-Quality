package com.example.hauiwaterquality.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.data.response.DataResponse
import com.example.hauiwaterquality.data.response.LoadingStatus
import com.example.hauiwaterquality.databinding.FragmentHomeBinding
import com.example.hauiwaterquality.ui.base.AbsBaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    @SuppressLint("SetTextI18n")
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
                    val ph = body.pH
                    val oxi = body.Oxi
                    when {
                        temperature in 20.0..30.0 -> {
                            binding.tvWarningTemp.text = "Nhiệt độ tốt"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                        temperature < 18 -> {
                            binding.tvWarningTemp.text = "Cảnh báo nhiệt độ thấp"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_good))
                        }
                        temperature > 33 -> {
                            binding.tvWarningTemp.text = "Cảnh báo nhiệt độ cao"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_good))
                        }
                        else -> {
                            binding.tvWarningTemp.text = "Nhiệt độ bình thường"
                            binding.layoutTemperature.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                    }

                    when {
                        oxi > 5 -> {
                            binding.tvWarningOxi.text = "Nồng độ Oxi tốt"
                            binding.layoutOxi.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                        oxi < 3.5 -> {
                            binding.tvWarningOxi.text = "Cảnh báo nồng độ Oxi thấp"
                            binding.layoutOxi.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_good))
                        }
                        else -> {
                            binding.tvWarningOxi.text = "Nồng độ Oxi bình thường"
                            binding.layoutOxi.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                    }

                    when {
                        ph in 7.5..8.5 -> {
                            binding.tvWarningPh.text = "Độ Ph tốt"
                            binding.layoutPh.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                        ph < 7 -> {
                            binding.tvWarningPh.text = "Cảnh báo độ Ph thấp"
                            binding.layoutPh.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_good))
                        }
                        ph > 9 -> {
                            binding.tvWarningPh.text = "Cảnh báo độ Ph cao"
                            binding.layoutPh.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.not_good))
                        }
                        else -> {
                            binding.tvWarningPh.text = "Độ Ph bình thường"
                            binding.layoutPh.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.good))
                        }
                    }

                    binding.tvTemperature.text = "${"%.3f".format(body.temperature)}℃"
                    binding.tvOxi.text = "${"%.3f".format(body.Oxi)} mg/l"
                    binding.tvPh.text = "%.3f".format(body.pH)
                    //binding.tvTimeTemperature.text = body.timestamps.toString()
                    binding.tvTimeTemperature.text = epochToIso8601(body.timestamps)
                    binding.tvTimeOxi.text = epochToIso8601(body.timestamps)
                    binding.tvTimePh.text = epochToIso8601(body.timestamps)
                    //binding.tvTimeTemperature.text = epochToIso8601(body.timestamps)

                }

            }
        }

    }

    private fun epochToIso8601(time: Long): String {
        val timeZ = time.toString().substring(0, time.toString().length-3).toLong()
        val format = "dd MMM yyyy - HH:mm:ss" // you can add the format you need
        val sdf = SimpleDateFormat(format, Locale.getDefault()) // default local
        sdf.timeZone = TimeZone.getDefault() // set anytime zone you need
        return sdf.format(Date(timeZ * 1000))
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