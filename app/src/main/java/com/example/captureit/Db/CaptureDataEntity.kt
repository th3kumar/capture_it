package com.example.captureit.Db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.captureit.models.BatteryStatus
import com.example.captureit.models.CaptureCount
import com.example.captureit.models.CaptureData
import com.example.captureit.models.InternetStatus
import com.example.captureit.models.Location

@Entity(tableName = "capture_data")
data class CaptureDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val internetStatus: Boolean,
    val isBatteryCharging: Boolean,
    val batteryPercentage: Int,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val photoUri: String
) {
    fun toCaptureData(): CaptureData {
        val internetStatusObj = InternetStatus(internetStatus)
        val batteryStatusObj = BatteryStatus(isBatteryCharging, 0)
        val locationObj = Location(latitude, longitude)
        val captureCountObj = CaptureCount(0)

        return CaptureData(
            internetStatus = internetStatusObj,
            batteryStatus = batteryStatusObj,
            location = locationObj,
            timestamp = timestamp,
            photoUri = photoUri,
            captureCount = captureCountObj
        )
    }

}


