package com.example.hauiwaterquality.ui.detail

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.data.api.responseremote.DataApp
import com.example.hauiwaterquality.data.response.DataResponse
import com.example.hauiwaterquality.data.response.LoadingStatus
import com.example.hauiwaterquality.databinding.FragmentDetailBinding
import com.example.hauiwaterquality.ui.base.AbsBaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailFragment : AbsBaseFragment<FragmentDetailBinding>() {
    private val mViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    private val listData = mutableListOf<DataApp>()
    private var average = 0F

    private lateinit var mLayoutManager: LinearLayoutManager

    private var temperatureAdapter = TemperatureAdapter()
    private var phtureAdapter = PhAdapter()
    private var oxiAdapter = OxiAdapter()

    override fun getLayout(): Int {
        return R.layout.fragment_detail
    }

    override fun initView() {
        mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rV.layoutManager = mLayoutManager
        when (args.keyDetail) {
            1 -> {
                binding.topAppBar.title = "Nhiệt độ"
                binding.rV.adapter = temperatureAdapter
            }
            2 -> {
                binding.topAppBar.title = "Nồng độ Oxi"
                binding.rV.adapter = oxiAdapter
            }
            3 -> {
                binding.topAppBar.title = "Độ Ph"
                binding.rV.adapter = phtureAdapter
            }

        }

        initRecycleView()
        binding.viewModel = mViewModel
        mViewModel.checkInternet(requireContext())
        mViewModel.getData()

        lifecycleScope.launch(Dispatchers.Default) {
            while (true) {
                delay(3000)
                mViewModel.checkInternet(requireContext())
                mViewModel.getData()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.turnOff -> {
                    showTurnOffDialog()
                    true
                }
                else -> {
                    false
                }
            }
        }

        observer()
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

                    if (listData.isNotEmpty()) {
                        if (body.timestamps != listData.first().timestamps) {
                            if (listData.size > 19) {
                                listData.removeLast()
                            }

                            updateData(body)
                        }
                    } else {

                        updateData(body)
                    }


                }

                if (it.loadingStatus == LoadingStatus.Error) {

                }

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateData(body: DataApp) {
        average = 0F
        listData.add(0, body)

        when (args.keyDetail) {
            1 -> {
                temperatureAdapter.submit(listData)
                listData.forEach {
                    average += it.temperature
                }
                average = (average / listData.size)

                if (average in 20.0..30.0) {
                    binding.tvAlert.text = "Nhiệt độ tốt"
                    binding.layoutBottom.setBackgroundResource(R.drawable.bg_bottom_sheet_good)
                } else if (average < 18) {
                    binding.tvAlert.text = "Cảnh báo nhiệt độ thấp"
                    binding.layoutBottom.setBackgroundResource(R.drawable.bg_bottom_sheet_not_good)
                } else if (average > 33) {
                    binding.tvAlert.text = "Cảnh báo nhiệt độ cao"
                    binding.layoutBottom.setBackgroundResource(R.drawable.bg_bottom_sheet_not_good)
                } else {
                    binding.tvAlert.text = "Nhiệt độ bình thường"
                    binding.layoutBottom.setBackgroundResource(R.drawable.bg_bottom_sheet_good)
                }


                binding.tvSum.text = "Số lần đo: ${listData.size}"
                binding.tvAverage.text =
                    "Nhiệt độ trung bình: ${(average * 1000.0).roundToInt() / 1000.0}°C"
            }
            2 -> {

            }
            3 -> {

            }

        }


    }

    /*private fun updateData() {
        val average = listData.map { it.temperature }.average()

        binding.tvAlert.text = when (average) {
            in 20.0..30.0 -> "Nhiệt độ tốt"
            in Double.MIN_VALUE..17.999 -> "Cảnh báo nhiệt độ thấp"
            in 33.001..Double.MAX_VALUE -> "Cảnh báo nhiệt độ cao"
            else -> "Nhiệt độ bình thường"
        }

        binding.layoutBottom.setBackgroundResource(
            if (average in 20.0..30.0) R.drawable.bg_bottom_sheet_good
            else R.drawable.bg_bottom_sheet_not_good
        )

        binding.tvSum.text = "Số lần đo: ${listData.size}"
        binding.tvAverage.text = "Nhiệt độ trung bình: ${"%.3f".format(average)}°C"
    }*/

    private fun initRecycleView() {

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