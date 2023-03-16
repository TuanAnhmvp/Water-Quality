package com.example.hauiwaterquality.ui.detail

import android.annotation.SuppressLint
import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.data.api.responseremote.DataApp
import com.example.hauiwaterquality.databinding.ItemTemperatureBinding
import com.example.hauiwaterquality.ui.base.BaseAdapter
import com.example.hauiwaterquality.ui.base.BaseViewHolder

class TemperatureAdapter : BaseAdapter<DataApp>(R.layout.item_temperature) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = holder.binding as ItemTemperatureBinding
        item.img.setBackgroundResource(R.drawable.thermometer)
        item.tvDetail.text = "${"%.3f".format(list[position].temperature)}°C"
    }

}