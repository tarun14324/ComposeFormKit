package com.tarun.composeformkit.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var instance: LeadDatabase? = null

    fun getDatabase(context: Context): LeadDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                LeadDatabase::class.java,
                "lead_database"
            ).build().also { instance = it }
        }
    }
}