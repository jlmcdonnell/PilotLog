package dev.mcd.pilotlog.ui.draftentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.draftentry.DraftEntryRepository
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import dev.mcd.pilotlog.domain.logbook.LogbookEntryError
import dev.mcd.pilotlog.domain.logbook.SaveLogbookEntry
import dev.mcd.pilotlog.domain.time.TimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class DraftEntryViewModel @Inject constructor(
    private val draftEntryRepository: DraftEntryRepository,
    private val saveLogbookEntry: SaveLogbookEntry,
) : ViewModel() {

    sealed class State {
        object Dismiss : State()
        class SaveError(val logbookEntryError: LogbookEntryError) : State()
    }

    val newEntries = flow {
        draftEntryRepository.getEntries()
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }

    val state: SharedFlow<State> get() = _state
    private val _state = MutableSharedFlow<State>()

    fun onDatePicked(date: Long) {
        viewModelScope.launch {
            val dateString = Instant.ofEpochMilli(date)
                .atOffset(ZoneOffset.ofHours(0))
                .toLocalDate()
                .toString()

            saveEntry {
                it.copy(date = dateString)
            }
        }
    }

    fun onDepartureArrivalTimesUpdated(departed: TimeString, arrived: TimeString) {
        viewModelScope.launch {
            saveEntry {
                it.copy(
                    departureTime = departed,
                    arrivalTime = arrived,
                )
            }
        }
    }

    fun onCaptainUpdated(captain: String) {
        viewModelScope.launch {
            saveEntry {
                it.copy(captain = captain)
            }
        }
    }

    fun onOperatingCapacityChanged(operatingCapacity: String) {
        viewModelScope.launch {
            saveEntry {
                it.copy(holdersOperatingCapacity = operatingCapacity)
            }
        }
    }

    fun onDiscardClicked() {
        viewModelScope.launch {
            draftEntryRepository.deleteEntry()
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            runCatching {
                when (val result = saveLogbookEntry.execute()) {
                    is SaveLogbookEntry.Result.Success -> {
                        _state.tryEmit(State.Dismiss)
                    }
                    is SaveLogbookEntry.Result.Error -> {
                        _state.tryEmit(State.SaveError(result.error))
                    }
                }
            }.onFailure {
                Timber.e(it, "saving entry")

            }
        }
    }

    private suspend fun saveEntry(update: (LogbookEntry) -> LogbookEntry) {
        val entry = draftEntryRepository.getEntry()
        draftEntryRepository.updateEntry(update(entry))
    }
}