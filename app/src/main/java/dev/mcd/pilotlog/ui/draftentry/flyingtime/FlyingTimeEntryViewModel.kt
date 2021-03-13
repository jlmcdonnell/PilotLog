package dev.mcd.pilotlog.ui.draftentry.flyingtime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.draftentry.DraftEntryRepository
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FlyingTimeEntryViewModel @Inject constructor(
    private val entryRepository: DraftEntryRepository
) : ViewModel() {

    sealed class State {
        object ShowError : State()
        object Dismiss : State()
    }

    val state: SharedFlow<State> get() = _state
    private val _state = MutableSharedFlow<State>()

    fun onDaySecondsUpdated(duration: Int) {
        saveEntry {
            it.copy(secondsDay = duration)
        }
    }

    fun onNightSecondsUpdated(duration: Int) {
        saveEntry {
            it.copy(secondsNight = duration)
        }
    }

    fun onInstrumentSecondsUpdated(duration: Int) {
        saveEntry {
            it.copy(secondsInstrument = duration)
        }
    }

    fun onInstrumentSimulatedSecondsUpdated(duration: Int) {
        saveEntry {
            it.copy(secondsInstrumentSim = duration)
        }
    }

    private fun saveEntry(update: (LogbookEntry) -> LogbookEntry) {
        viewModelScope.launch {
            runCatching {
                val updated = update(entryRepository.getEntry())
                entryRepository.updateEntry(updated)
            }.onFailure {
                Timber.e(it, "updating entry")
                _state.emit(State.ShowError)
            }
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            _state.emit(State.Dismiss)
        }
    }
}
