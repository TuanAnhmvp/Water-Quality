package com.example.hauiwaterquality.ui.detail

import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.data.api.responseremote.DataApp
import com.example.hauiwaterquality.databinding.ItemTemperatureBinding
import com.example.hauiwaterquality.ui.base.BaseAdapter
import com.example.hauiwaterquality.ui.base.BaseViewHolder

class OxiAdapter : BaseAdapter<DataApp>(R.layout.item_temperature) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = holder.binding as ItemTemperatureBinding
        item.img.setBackgroundResource(R.drawable.o2)
        item.tvDetail.text = "${list[position].temperature}oxi"
    }

}