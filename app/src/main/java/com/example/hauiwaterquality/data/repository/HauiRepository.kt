package com.example.hauiwaterquality.data.repository

import com.example.hauiwaterquality.data.api.RemoteServices
import com.example.hauiwaterquality.data.api.responseremote.DataApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HauiRepository @Inject constructor(private val remoteServices: RemoteServices) {

    suspend fun getData(): DataApp? =
        withContext(Dispatchers.IO) {
            try {
                remoteServices.getData()
            } catch (ex: Exception) {
                null
            }
        }

}