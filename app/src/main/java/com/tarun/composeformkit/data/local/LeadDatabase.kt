package com.tarun.composeformkit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LeadEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LeadDatabase : RoomDatabase() {
    abstract fun leadDao(): LeadDao
}