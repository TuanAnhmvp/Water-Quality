package com.example.hauiwaterquality.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hauiwaterquality.data.api.responseremote.DataApp
import com.example.hauiwaterquality.data.repository.RemoteRepository
import com.example.hauiwaterquality.data.response.DataResponse
import com.example.hauiwaterquality.utils.CheckInternet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val remoteRepository: RemoteRepository) :
    ViewModel() {

    val dataResponseLiveData = MutableLiveData<DataResponse<DataApp>?>(DataResponse.DataIdle())
    val checkInternet = MutableLiveData<Boolean>()

    fun getData() {
        //dataResponseLiveData.value = DataResponse.DataLoading(LoadingStatus.Loading)
        viewModelScope.launch {
            val result = remoteRepository.getData()
            Log.d("fasdfasdf", "ressult = $result")
            if (result != null) {
                dataResponseLiveData.postValue(DataResponse.DataSuccess(result))
            } else {
                dataResponseLiveData.postValue(DataResponse.DataError())
            }
        }
    }

    fun checkInternet(context: Context) {
        if (checkForInternet(context)) {
            checkInternet.postValue(true)
        } else {
            checkInternet.postValue(false)
        }
    }

    private fun checkForInternet(context: Context): Boolean {
        return CheckInternet.checkForInternet(context)
    }

}