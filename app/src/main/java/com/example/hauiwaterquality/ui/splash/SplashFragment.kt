package com.example.hauiwaterquality.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.databinding.FragmentSplashBinding
import com.example.hauiwaterquality.ui.base.AbsBaseFragment

class SplashFragment : AbsBaseFragment<FragmentSplashBinding>() {
    private val handler = Handler(Looper.myLooper()!!)
    private val runnable = Runnable {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
    }

    override fun getLayout(): Int {
        return R.layout.fragment_splash
    }

    override fun initView() {
    }

    override fun onStart() {

        binding.imgIconSplash.animate().apply {
            duration = 1000
            scaleX(0.5F)
            scaleY(0.5F)
            scaleXBy(1F)
            scaleYBy(1F)


        }

        binding.imgCircleSplash.animate().apply {
            duration = 1000
            scaleX(0F)
            scaleY(0F)
            scaleXBy(2F)
            scaleYBy(2F)

        }

        super.onStart()


        handler.postDelayed(runnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }


}