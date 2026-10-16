package com.tarun.composeformkit.lead.model

data class LeadSaveState(
    val isSaving: Boolean = false,
    val error: String? = null
)