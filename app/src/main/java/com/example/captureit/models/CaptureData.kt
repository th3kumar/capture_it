package com.example.captureit.models

data class CaptureData(
    val internetStatus: InternetStatus,
    val batteryStatus: BatteryStatus,
    val location: Location,
    val timestamp: Long,
    val photoUri: String,
    val captureCount: CaptureCount = CaptureCount(0)
)

