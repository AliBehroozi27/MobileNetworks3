package com.example.network

import com.example.network.throughput.DataHolder
import com.example.network.throughput.ThroughputMonitoringServiceImpl
import retrofit2.http.Body
import retrofit2.http.POST

interface SampleUploadService {
    @POST("api/users")
    suspend fun postRequest(@Body body: DataHolder)

    companion object {
        const val BASE_URL = "https://reqres.in/"
    }
}

