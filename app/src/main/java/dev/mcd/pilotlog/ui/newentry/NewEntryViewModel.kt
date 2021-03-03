package dev.mcd.pilotlog.ui.newentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.entry.Entry
import dev.mcd.pilotlog.domain.newentry.NewEntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class NewEntryViewModel @Inject constructor(
    private val newEntryRepository: NewEntryRepository,
) : ViewModel() {

    val newEntries = flow {
        newEntryRepository.getEntries()
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }

    private val latestEntry get() = suspend { newEntries.first() }

    fun onDatePicked(date: Long) {
        viewModelScope.launch {
            val dateString = Instant.ofEpochMilli(date)
                .atOffset(ZoneOffset.ofHours(0))
                .toLocalDate()
                .toString()

            val update = latestEntry().copy(date = dateString)
            saveEntry(update)
        }
    }

    fun onCaptainUpdated(captain: String) {
        viewModelScope.launch {
            val entry = newEntryRepository.getEntry()
                .copy(captain = captain)
            saveEntry(entry)
        }
    }

    fun onOperatingCapacityChanged(operatingCapacity: String) {
        viewModelScope.launch {
            val entry = newEntryRepository.getEntry()
                .copy(holdersOperatingCapacity = operatingCapacity)
            saveEntry(entry)
        }
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            newEntryRepository.deleteEntry()
        }
    }

    fun onSaveClicked() {
    }

    private suspend fun saveEntry(entry: Entry) {
        newEntryRepository.updateEntry(entry)
    }
}
