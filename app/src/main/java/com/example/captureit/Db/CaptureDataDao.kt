package com.example.captureit.Db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CaptureDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaptureData(data: CaptureDataEntity)

    @Query("SELECT * FROM capture_data ORDER BY timestamp DESC LIMIT 1")
    fun getLatestCaptureData(): LiveData<CaptureDataEntity>
}
