package com.example.network.throughput

interface ThroughputMonitoringService {
    suspend fun getEndToEndDownstreamBandwidth(): Int

    suspend fun getEndToEndUpstreamBandwidth(): Int
}