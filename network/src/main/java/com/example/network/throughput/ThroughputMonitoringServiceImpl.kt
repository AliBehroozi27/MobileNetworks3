package com.example.network.throughput

import javax.inject.Inject
import com.example.network.SampleUploadService
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class ThroughputMonitoringServiceImpl @Inject constructor(
    private val sampleUploadService: SampleUploadService
) : ThroughputMonitoringService {

    private val okHttpClient = OkHttpClient()

    override suspend fun getEndToEndDownstreamBandwidth(): Int = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(DUCK_FILE_URL)
            .build()
        var response: Response? = null

        val time = measureTimeMillis {
            response = okHttpClient.newCall(request).execute()
        }
        val contentLength = response!!.body!!.contentLength()

        return@withContext ((contentLength / (1024f * time)) * 1000f).toInt()
    }

    override suspend fun getEndToEndUpstreamBandwidth(): Int = withContext(Dispatchers.IO) {
        val uploadSize = 50 * 1024

        val time = measureTimeMillis {
            sampleUploadService.postRequest(DataHolder(uploadSize))
        }
        return@withContext ((uploadSize / (1024f)) / (time / 1000f)).toInt()
    }

    companion object {
        const val DUCK_FILE_URL =
            "https://upload.wikimedia.org/wikipedia/commons/a/a6/Parrulo_-Muscovy_duckling.jpg?download"
    }
}

class DataHolder(sizeInBytes: Int) {
    val name = (1..sizeInBytes).map { 'c' }.toList().joinToString(separator = "")
    val job = "."
}

