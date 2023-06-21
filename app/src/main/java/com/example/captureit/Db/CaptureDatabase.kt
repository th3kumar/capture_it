package com.example.captureit.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.captureit.models.CaptureData

@Database(entities = [CaptureData::class], version = 1)
abstract class CaptureDatabase : RoomDatabase() {


    abstract fun captureDataDao(): CaptureDataDao

    companion object {
        @Volatile
        private var instance: CaptureDatabase? = null

        fun getInstance(context: Context): CaptureDatabase {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    CaptureDatabase::class.java,
                    "capture_database"
                ).build()
                instance = database
                database
            }
        }
    }
}