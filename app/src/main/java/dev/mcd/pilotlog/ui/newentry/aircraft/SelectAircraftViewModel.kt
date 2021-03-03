package dev.mcd.pilotlog.ui.newentry.aircraft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.AircraftRepository
import dev.mcd.pilotlog.domain.newentry.NewEntryRepository
import dev.mcd.pilotlog.ui.newentry.aircraft.SelectAircraftViewModel.State.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SelectAircraftViewModel @Inject constructor(
    private val newEntryRepository: NewEntryRepository,
    private val aircraftRepository: AircraftRepository,
) : ViewModel() {

    sealed class State {
        object Dismiss : State()
        object ShowLoadError : State()
        object ShowUpdateError : State()
        object NavigateAddAircraft : State()
    }

    val state: SharedFlow<State> get() = _state

    val aircrafts = flow {
        aircraftRepository.get()
            .flowOn(Dispatchers.IO)
            .catch {
                Timber.e(it, "loading aircrafts")
                _state.emit(ShowLoadError)
            }
            .collect(::emit)
    }

    private val _state = MutableSharedFlow<State>()

    fun onNewAircraftClicked() {
        viewModelScope.launch {
            _state.emit(NavigateAddAircraft)
        }
    }

    fun onAircraftSelected(aircraft: Aircraft) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    val entry = newEntryRepository.getEntry()
                        .copy(aircraft = aircraft)

                    newEntryRepository.updateEntry(entry)
                }
                _state.emit(Dismiss)
            }.onFailure {
                Timber.e(it, "updating new entry with aircraft")
                _state.emit(ShowUpdateError)
            }
        }
    }
}
