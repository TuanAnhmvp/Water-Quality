package com.example.hauiwaterquality.ui.login

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hauiwaterquality.R
import com.example.hauiwaterquality.data.response.DataResponse
import com.example.hauiwaterquality.data.response.LoadingStatus
import com.example.hauiwaterquality.databinding.FragmentLoginBinding
import com.example.hauiwaterquality.ui.base.AbsBaseFragment
import com.example.hauiwaterquality.utils.CheckInternet
import com.example.hauiwaterquality.utils.ToastUtils
import com.example.hauiwaterquality.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : AbsBaseFragment<FragmentLoginBinding>() {
    private val mViewModel: LoginViewModel by viewModels()

    override fun getLayout(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        binding.viewModel = mViewModel
        onBackPressed()
        observer()
        binding.btnLogin.setOnClickListener {
            Utils.hideKeyboard(requireContext(), it)
            if (binding.edtUserName.text.toString()
                    .isNotEmpty() && binding.edtPassWord.text.toString().isNotEmpty()
            ) {
                if (checkForInternet(requireContext())) {
                    mViewModel.checkLogin()
                } else {
                    ToastUtils.getInstance(requireContext())
                        .showToast(getString(R.string.kh_ng_c_k_t_n_i_internet))

                }
            } else {
                Snackbar.make(
                    binding.layoutRoot,
                    "Vui lòng nhập tên đăng nhập và mật khẩu",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }


        }
    }

    private fun checkForInternet(context: Context): Boolean {
        return CheckInternet.checkForInternet(context)
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dang_nhap_khong_thanh_cong))
            .setMessage(resources.getString(R.string.ten_tai_khoan_hoac_mat_khau_khong_chinh_xac))
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                // Respond to positive button press
            }
            .show()
    }

    private fun observer() {
        mViewModel.loginResponseLiveData.observe(this) {
            Log.d("fasdfsdf", "it = $it")
            it?.let {
                if (it.loadingStatus == LoadingStatus.Loading) {

                }
                if (it.loadingStatus == LoadingStatus.Success) {
                    val body = (it as DataResponse.DataSuccess).body
                    if (body.username == binding.edtUserName.text.toString() && body.password == binding.edtPassWord.text.toString()) {
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    } else {
                        showAlertDialog()
                    }
                }
                if (it.loadingStatus == LoadingStatus.Error) {
                    Snackbar.make(
                        binding.layoutRoot,
                        "Máy chủ không hoạt động",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    private fun onBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()

            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

}