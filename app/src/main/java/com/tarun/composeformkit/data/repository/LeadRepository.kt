package com.tarun.composeformkit.data.repository

import com.tarun.composeformkit.data.local.LeadDao
import com.tarun.composeformkit.data.local.LeadEntity
import kotlinx.coroutines.flow.Flow

class LeadRepository(
    private val leadDao: LeadDao
) {
    fun observeLeads(): Flow<List<LeadEntity>> {
        return leadDao.observeLeads()
    }

    suspend fun saveLead(lead: LeadEntity) {
        leadDao.insertLead(lead)
    }

    suspend fun deleteLead(id: Long) {
        leadDao.deleteLead(id)
    }
}