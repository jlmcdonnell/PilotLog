package dev.mcd.pilotlog.ui.draftentry.aircraft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.AircraftRepository
import dev.mcd.pilotlog.ui.draftentry.aircraft.AddAircraftViewModel.State.Dismiss
import dev.mcd.pilotlog.ui.draftentry.aircraft.AddAircraftViewModel.State.ShowUpdateError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddAircraftViewModel @Inject constructor(
    private val aircraftRepository: AircraftRepository,
) : ViewModel() {

    sealed class State {
        object Dismiss : State()
        object ShowUpdateError : State()
    }

    val state: SharedFlow<State> get() = _state

    private val _state = MutableSharedFlow<State>()

    fun onSaveAircraft(aircraft: Aircraft) {
        viewModelScope.launch {
            runCatching {
                aircraftRepository.save(aircraft)
                _state.emit(Dismiss)
            }.onFailure {
                Timber.e(it, "saving aircraft")
                _state.emit(ShowUpdateError)
            }
        }
    }
}
