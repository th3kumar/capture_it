package com.example.captureit.Db

import androidx.lifecycle.LiveData

class CaptureDataRepository(private val captureDataDao: CaptureDataDao) {

    suspend fun insertCaptureData(data: CaptureDataEntity) {
        captureDataDao.insertCaptureData(data)
    }

    fun getLatestCaptureData(): LiveData<CaptureDataEntity> {
        return captureDataDao.getLatestCaptureData()
    }
}
