package com.example.captureit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.captureit.Db.CaptureDataEntity
import com.example.captureit.Db.CaptureDataRepository
import com.example.captureit.models.BatteryStatus
import com.example.captureit.models.Location
import kotlinx.coroutines.launch
import java.util.Calendar

class CaptureViewModel(private val repository: CaptureDataRepository) : ViewModel() {

    private val _captureData = MutableLiveData<CaptureDataEntity>()
    val captureData: LiveData<CaptureDataEntity>
        get() = _captureData

    // Function to capture data and photo
    fun captureData() {

        val batteryStatus = getBatteryStatus()

        val captureData = CaptureDataEntity(
            timestamp = Calendar.getInstance().timeInMillis,
            internetStatus = isInternetAvailable(),
            isBatteryCharging = batteryStatus.isCharging,
            batteryPercentage = batteryStatus.batteryPercentage,
            latitude = getLocation().latitude,
            longitude = getLocation().longitude,
            // TODO: Get the photo URI
            photoUri = ""
        )

        // Save data locally
        saveDataLocally(captureData)

        // Check internet connectivity and save data to Firebase if available
        if (captureData.internetStatus) {
            saveDataToFirebase(captureData)
        }
    }

    private fun getBatteryStatus(): BatteryStatus {
        // TODO: Implement the logic to get the battery status
        // Return the BatteryStatus object with the appropriate values
        return BatteryStatus(isCharging = false, batteryPercentage = 100)
    }

    // Function to get location
    private fun getLocation(): Location {
        // TODO: Implement the logic to get the location
        // Return the Location object with the appropriate latitude and longitude
        return Location(latitude = 0.0, longitude = 0.0)
    }

    // Function to save data locally
    private fun saveDataLocally(data: CaptureDataEntity) {
        viewModelScope.launch {
            repository.insertCaptureData(data)
        }
    }

    // Function to save data to Firebase
    private fun saveDataToFirebase(data: CaptureDataEntity) {
        // TODO: Implement the logic for saving data to Firebase
    }

    // Function to check internet connectivity
    private fun isInternetAvailable(): Boolean {
        // TODO: Implement the logic for checking internet connectivity
        return false
    }
}


