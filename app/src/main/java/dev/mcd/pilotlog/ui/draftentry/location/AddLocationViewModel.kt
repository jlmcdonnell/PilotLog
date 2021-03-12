package dev.mcd.pilotlog.ui.draftentry.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.location.Location
import dev.mcd.pilotlog.domain.location.LocationRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
) : ViewModel() {

    sealed class State {
        object Dismiss : State()
    }

    val state: SharedFlow<State> get() = _state
    private val _state = MutableSharedFlow<State>()

    private var location: Location? = null

    fun onLocationUpdated(location: Location) {
        this.location = location
    }

    fun onAddClicked() {
        viewModelScope.launch {
            location?.let { locationRepository.save(it) }
            _state.emit(State.Dismiss)
        }
    }
}
