package com.example.hauiwaterquality.data.repository

import com.example.hauiwaterquality.data.api.RemoteServices
import com.example.hauiwaterquality.data.api.responseremote.DataApp
import com.example.hauiwaterquality.data.api.responseremote.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteRepository @Inject constructor(private val remoteServices: RemoteServices) {

    suspend fun getData(): DataApp? =
        withContext(Dispatchers.IO) {
            try {
                remoteServices.getData()
            } catch (ex: Exception) {
                null
            }
        }

    suspend fun checkLogin(): LoginResponse? =
        withContext(Dispatchers.IO) {
            delay(1000)
            try {
                remoteServices.checkLogin()
            } catch (ex: Exception) {
                null
            }
        }

}