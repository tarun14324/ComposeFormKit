package com.tarun.composeformkit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LeadDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLead(lead: LeadEntity): Long

    @Query("SELECT * FROM leads ORDER BY id DESC")
    fun observeLeads(): Flow<List<LeadEntity>>

    @Query("DELETE FROM leads WHERE id = :id")
    suspend fun deleteLead(id: Long)
}