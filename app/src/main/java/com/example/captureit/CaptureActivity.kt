package com.example.captureit

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.captureit.Db.CaptureDataRepository
import com.example.captureit.Db.CaptureDatabase
import com.example.captureit.models.CaptureData
import com.example.captureit.viewModel.CaptureViewModel
import com.example.captureit.viewModel.CaptureViewModelFactory
import com.google.firebase.FirebaseApp
import java.util.concurrent.TimeUnit

class CaptureActivity : AppCompatActivity() {

    private lateinit var viewModel: CaptureViewModel

    // Define your views as properties
    private lateinit var timestampTextView: TextView
    private lateinit var capturedCountTextView: TextView
    private lateinit var batteryChargingStatusTextView: TextView
    private lateinit var batteryPercentageTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var captureButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        // Connect your views using findViewById
        timestampTextView = findViewById(R.id.timestampTextView)
        capturedCountTextView = findViewById(R.id.capturedCountTextView)
        batteryChargingStatusTextView = findViewById(R.id.batteryChargingStatusTextView)
        batteryPercentageTextView = findViewById(R.id.batteryPercentageTextView)
        locationTextView = findViewById(R.id.locationTextView)
        captureButton = findViewById(R.id.captureButton)

        // Connectivity check
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected = networkInfo != null && networkInfo.isConnected

        // Initialize the ViewModel
        val database = CaptureDatabase.getInstance(applicationContext)
        val repository = CaptureDataRepository(database.captureDataDao())
        viewModel = ViewModelProvider(this, CaptureViewModelFactory(repository)).get(CaptureViewModel::class.java)

        // Observe the captured data and update the UI
        viewModel.captureData.observe(this, { captureDataEntity ->
            val captureData = captureDataEntity.toCaptureData()
            // Update the UI with the latest captured data
            updateUI(captureData)
        })

        // Set a click listener on the Capture Button
        captureButton.setOnClickListener {
            // Capture the data
            viewModel.captureData()

            // Show a toast message or perform any other action
            Toast.makeText(this, "Data captured!", Toast.LENGTH_SHORT).show()
        }

        // Start a periodic job to upload data to Firebase every 15 minutes
        startPeriodicUploadJob()
    }

    private fun startPeriodicUploadJob() {
        val uploadWorkRequest = PeriodicWorkRequestBuilder<UploadDataWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "UploadDataWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            uploadWorkRequest
        )
    }

    private fun updateUI(data: CaptureData) {
        // Update the UI with the captured data
        timestampTextView.text = data.timestamp.toString()
        // Set the captured image to the ImageView
        // ...
        capturedCountTextView.text = data.captureCount.toString()
        batteryChargingStatusTextView.text = data.batteryStatus.isCharging.toString()
        batteryPercentageTextView.text = data.batteryStatus.batteryPercentage.toString()
        locationTextView.text = "Latitude: ${data.location.latitude}, Longitude: ${data.location.longitude}"
    }
}



