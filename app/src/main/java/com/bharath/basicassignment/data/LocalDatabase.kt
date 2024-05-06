package com.bharath.basicassignment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bharath.basicassignment.data.entity.VideoEntity

@Database(
    entities = [
        VideoEntity::class,
    ], version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract val dao: LocalDao
}