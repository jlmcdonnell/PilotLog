package dev.mcd.pilotlog.ui.newentry.destination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.domain.destination.DestinationRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDestinationViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository,
) : ViewModel() {

    sealed class State {
        object Dismiss : State()
    }

    val state: SharedFlow<State> get() = _state
    private val _state = MutableSharedFlow<State>()

    private var destination: Destination? = null

    fun onDestinationUpdated(destination: Destination) {
        this.destination = destination
    }

    fun onAddClicked() {
        viewModelScope.launch {
            destination?.let { destinationRepository.save(it) }
            _state.emit(State.Dismiss)
        }
    }
}
