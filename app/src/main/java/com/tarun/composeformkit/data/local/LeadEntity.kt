package com.tarun.composeformkit.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leads")
data class LeadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val email: String,
    val leadType: String,
    val companyName: String = "",
    val phone: String,
    val notes: String,
    val consent: Boolean
)