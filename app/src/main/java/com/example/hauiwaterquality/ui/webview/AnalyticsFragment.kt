package com.example.hauiwaterquality.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.databinding.FragmentAnalyticsBinding
import com.example.hauiwaterquality.ui.base.AbsBaseFragment

class AnalyticsFragment : AbsBaseFragment<FragmentAnalyticsBinding>() {

    override fun getLayout(): Int {
        return R.layout.fragment_analytics
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {

        binding.webView.loadUrl("https://notebooknew-ruli.notebook.us-east-1.sagemaker.aws/notebooks/IoTAnalytics/Untitled.ipynb")


        binding.webView.settings.javaScriptEnabled = true


        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

}