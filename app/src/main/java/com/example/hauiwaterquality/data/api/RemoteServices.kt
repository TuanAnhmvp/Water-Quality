package com.example.hauiwaterquality.data.api

import com.example.hauiwaterquality.data.api.responseremote.DataApp
import com.example.hauiwaterquality.data.api.responseremote.LoginResponse
import retrofit2.http.*


interface RemoteServices {

    @GET("/myKey")
    suspend fun getData(
    ): DataApp?

    @GET("/userRoot")
    suspend fun checkLogin(
    ): LoginResponse?


}
