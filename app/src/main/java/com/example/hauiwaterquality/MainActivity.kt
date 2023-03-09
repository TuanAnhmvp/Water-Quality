package com.example.hauiwaterquality

import com.example.hauiwaterquality.databinding.ActivityMainBinding
import com.example.hauiwaterquality.ui.base.AbsBaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AbsBaseActivity<ActivityMainBinding>() {
    override fun getFragmentID(): Int {
        return R.id.navContainerViewMain
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}