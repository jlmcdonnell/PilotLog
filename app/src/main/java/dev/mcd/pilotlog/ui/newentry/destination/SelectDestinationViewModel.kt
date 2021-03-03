package dev.mcd.pilotlog.ui.newentry.destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.destination.DestinationRepository
import dev.mcd.pilotlog.domain.newentry.NewEntryRepository
import dev.mcd.pilotlog.ui.newentry.destination.SelectDestinationParams.ModeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SelectDestinationParams {
    ModeFrom,
    ModeTo
}

@HiltViewModel
class SelectDestinationViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository,
    private val newEntryRepository: NewEntryRepository,
) : ViewModel() {

    sealed class State {
        object Dismiss : State()
        object ShowLoadError : State()
    }

    lateinit var params: SelectDestinationParams

    val destinations = flow {
        destinationRepository.get()
            .flowOn(Dispatchers.IO)
            .catch { _state.emit(State.ShowLoadError) }
            .collect(::emit)
    }

    val state: SharedFlow<State> get() = _state

    private val _state = MutableSharedFlow<State>()

    fun onDestinationSelected(destination: Destination) {
        viewModelScope.launch {
            if (params == ModeFrom) {
                newEntryRepository.updateFromDestination(destination)
            } else {
                newEntryRepository.updateToDestination(destination)
            }
            _state.emit(State.Dismiss)
        }
    }
}
