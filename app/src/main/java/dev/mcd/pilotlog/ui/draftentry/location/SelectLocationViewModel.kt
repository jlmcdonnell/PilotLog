package dev.mcd.pilotlog.ui.draftentry.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.draftentry.DraftEntryRepository
import dev.mcd.pilotlog.domain.location.Location
import dev.mcd.pilotlog.domain.location.LocationRepository
import dev.mcd.pilotlog.ui.draftentry.location.SelectLocationParams.ModeDeparture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SelectLocationParams {
    ModeDeparture,
    ModeArrival
}

@HiltViewModel
class SelectLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val draftEntryRepository: DraftEntryRepository,
) : ViewModel() {

    sealed class State {
        object Dismiss : State()
        object ShowLoadError : State()
    }

    lateinit var params: SelectLocationParams

    val locations = flow {
        locationRepository.get()
            .flowOn(Dispatchers.IO)
            .catch { _state.emit(State.ShowLoadError) }
            .collect(::emit)
    }

    val state: SharedFlow<State> get() = _state

    private val _state = MutableSharedFlow<State>()

    fun onLocationSelected(location: Location) {
        viewModelScope.launch {
            if (params == ModeDeparture) {
                draftEntryRepository.updateDeparture(location)
            } else {
                draftEntryRepository.updateArrival(location)
            }
            _state.emit(State.Dismiss)
        }
    }
}
