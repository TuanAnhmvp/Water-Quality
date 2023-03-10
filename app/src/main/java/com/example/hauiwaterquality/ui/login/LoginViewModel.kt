package com.example.hauiwaterquality.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.example.hauiwaterquality.data.api.responseremote.DataApp
import com.example.hauiwaterquality.data.api.responseremote.LoginResponse
import com.example.hauiwaterquality.data.repository.RemoteRepository
import com.example.hauiwaterquality.data.response.DataResponse
import com.example.hauiwaterquality.data.response.LoadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val remoteRepository: RemoteRepository): ViewModel() {
    val loginResponseLiveData = MutableLiveData<DataResponse<LoginResponse>?>(DataResponse.DataIdle())

    val isLoading: LiveData<Boolean>
        get() = Transformations.map(loginResponseLiveData) {
            loginResponseLiveData.value!!.loadingStatus == LoadingStatus.Loading
        }


    fun checkLogin() {
        loginResponseLiveData.value = DataResponse.DataLoading(LoadingStatus.Loading)
        viewModelScope.launch {
            val result = remoteRepository.checkLogin()
            Log.d("fasdfasdf", "ressult2 = $result")
            if (result != null) {
                loginResponseLiveData.postValue(DataResponse.DataSuccess(result))
            } else {
                loginResponseLiveData.postValue(DataResponse.DataError())
            }
        }
    }
}