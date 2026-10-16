package com.tarun.composeformkit.lead.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tarun.composeformkit.data.local.LeadEntity
import com.tarun.composeformkit.data.repository.LeadRepository
import com.tarun.composeformkit.lead.model.LeadSaveState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


sealed interface LeadEvent {
    data object Saved : LeadEvent
}

class LeadViewModel(
    private val repository: LeadRepository
) : ViewModel() {

    val leads = repository.observeLeads().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _saveState = MutableStateFlow(LeadSaveState())
    val saveState = _saveState.asStateFlow()

    private val _events = MutableSharedFlow<LeadEvent>()
    val events = _events.asSharedFlow()

    fun addLead(values: Map<String, String>) {
        if (_saveState.value.isSaving) return

        val lead = LeadEntity(
            fullName = values["fullName"].orEmpty().trim(),
            email = values["email"].orEmpty().trim(),
            leadType = values["leadType"].orEmpty(),
            companyName = values["companyName"].orEmpty().trim(),
            phone = values["phone"].orEmpty().trim(),
            notes = values["notes"].orEmpty(),
            consent = values["consent"] == "true"
        )

        viewModelScope.launch {
            _saveState.value = LeadSaveState(isSaving = true)

            try {
                repository.saveLead(lead)

                _saveState.value = LeadSaveState()
                delay(2000L.milliseconds)
                _events.emit(LeadEvent.Saved)
            } catch (exception: Exception) {
                _saveState.value = LeadSaveState(
                    error = "Unable to save lead. Please try again."
                )
            }
        }
    }

    fun clearSaveError() {
        _saveState.value = _saveState.value.copy(error = null)
    }

    fun deleteLead(id: Long) {
        viewModelScope.launch {
            repository.deleteLead(id)
        }
    }
}

class LeadViewModelFactory(
    private val repository: LeadRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(LeadViewModel::class.java)) {
            return LeadViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}
