package com.example.hauiwaterquality.data.api

import com.example.hauiwaterquality.data.api.responseremote.DataApp
import retrofit2.http.*


interface RemoteServices {

    @GET("/mykey")
    suspend fun getData(
    ): DataApp?


}
