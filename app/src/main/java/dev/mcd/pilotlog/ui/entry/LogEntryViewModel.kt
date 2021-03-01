package dev.mcd.pilotlog.ui.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.pilotlog.domain.date.DateString
import dev.mcd.pilotlog.domain.entry.Destination
import dev.mcd.pilotlog.domain.entry.interactor.GetNewEntryDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogEntryViewModel @Inject constructor(
    private val getNewEntryDetails: GetNewEntryDetails,
) : ViewModel() {

    private val _entryDate = MutableSharedFlow<DateString>()
    private val _fromDestination = MutableSharedFlow<Destination>(replay = 1)
    private val _toDestination = MutableSharedFlow<Destination>(replay = 1)

    val entryDate: SharedFlow<DateString> get() = _entryDate
    val fromDestination: SharedFlow<Destination> get() = _fromDestination
    val toDestination: SharedFlow<Destination> get() = _toDestination

    fun start() {
        viewModelScope.launch {
            val newEntryDetails = getNewEntryDetails.execute()
            _entryDate.tryEmit(newEntryDetails.date)
        }
    }

    fun onFromDestinationSelected(destination: Destination) {
        _fromDestination.tryEmit(destination)
    }

    fun onToDestinationSelected(destination: Destination) {
        _toDestination.tryEmit(destination)
    }

}
