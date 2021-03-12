package dev.mcd.pilotlog.ui.draftentry

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.domain.aircraft.isValid
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import dev.mcd.pilotlog.domain.logbook.LogbookEntryError
import dev.mcd.pilotlog.domain.time.DateString
import dev.mcd.pilotlog.ui.draftentry.DraftEntryFragmentDirections.toSelectAircraft
import dev.mcd.pilotlog.ui.draftentry.DraftEntryFragmentDirections.toSelectLocation
import dev.mcd.pilotlog.ui.draftentry.DraftEntryViewModel.State
import dev.mcd.pilotlog.ui.draftentry.location.SelectLocationParams
import kotlinx.android.synthetic.main.draft_entry_fragment.*
import kotlinx.android.synthetic.main.operating_capacity_layout.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DraftEntryFragment : Fragment(R.layout.draft_entry_fragment) {

    private val viewModel by viewModels<DraftEntryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        discardButton.setOnClickListener {
            viewModel.onDiscardClicked()
        }
        captainEditText.onCaptainUpdated = { captain ->
            viewModel.onCaptainUpdated(captain)
        }
        operatingCapacityView.onOperatingCapacityChanged = {
            viewModel.onOperatingCapacityChanged(it)
        }
        departureLocation.onSelectLocationClicked = {
            selectDeparture()
        }
        arrivalLocation.onSelectLocationClicked = {
            selectArrival()
        }
        aircraftView.setOnClickListener {
            clearInputFocus()
            findNavController().navigate(toSelectAircraft())
        }
        dateView.setOnClickListener {
            showDatePicker()
        }
        hocP1.setOnClickListener {
            operatingCapacityEditText.setText(hocP1.text)
        }
        hocPut.setOnClickListener {
            operatingCapacityEditText.setText(hocPut.text)
        }
        saveButton.setOnClickListener {
            viewModel.onSaveClicked()
        }
        with(departArrivalEntryView) {
            provideFragmentManager = { childFragmentManager }
            onTimesUpdated = { depart, arrive ->
                viewModel.onDepartureArrivalTimesUpdated(depart, arrive)
            }
        }
    }

    private fun setupVM() {
        viewModel.newEntries
            .onEach(::displayEntry)
            .launchIn(lifecycleScope)

        viewModel.state
            .onEach(::handleState)
            .launchIn(lifecycleScope)
    }

    private fun selectDeparture() {
        clearInputFocus()
        findNavController().navigate(toSelectLocation(SelectLocationParams.ModeDeparture))
    }

    private fun selectArrival() {
        clearInputFocus()
        findNavController().navigate(toSelectLocation(SelectLocationParams.ModeArrival))
    }

    private fun showDatePicker() {
        clearInputFocus()

        MaterialDatePicker.Builder.datePicker()
            .setSelection(System.currentTimeMillis())
            .build()
            .also {
                it.addOnPositiveButtonClickListener { date ->
                    viewModel.onDatePicked(date)
                }
                it.show(parentFragmentManager, null)
            }
    }

    private fun displayEntry(logbookEntry: LogbookEntry) {
        captainEditText.setText(logbookEntry.captain)
        captainEditText.setSelection(captainEditText.length())

        operatingCapacityEditText.setText(logbookEntry.holdersOperatingCapacity)
        operatingCapacityEditText.setSelection(operatingCapacityEditText.length())

        departureLocation.setLocation(logbookEntry.departure)
        arrivalLocation.setLocation(logbookEntry.arrival)

        displayEntryDate(logbookEntry.date)
        displayAircraft(logbookEntry.aircraft)

        departArrivalEntryView.arrival = logbookEntry.arrivalTime
        departArrivalEntryView.departure = logbookEntry.departureTime
    }

    private fun displayEntryDate(date: DateString) {
        if (date.isNotBlank()) {
            dateText.text = date
        } else {
            dateText.setText(R.string.entry_set_date)
        }
    }

    private fun displayAircraft(aircraft: Aircraft) {
        aircraftView.aircraft = aircraft

        if (aircraft.isValid) {
            setAircraftButton.isInvisible = true
            aircraftLabel.isInvisible = true
        } else {
            setAircraftButton.isVisible = true
            aircraftLabel.isVisible = true
        }
    }

    private fun handleState(state: State) {
        when (state) {
            is State.Dismiss -> findNavController().navigateUp()
            is State.SaveError -> displayLogEntryError(state.logbookEntryError)
        }
    }

    private fun displayLogEntryError(error: LogbookEntryError) {
        val errorResId = when (error) {
            LogbookEntryError.Aircraft -> R.string.entry_error_aircraft
            LogbookEntryError.ArrivalTime -> R.string.entry_error_arrivalTime
            LogbookEntryError.Captain -> R.string.entry_error_captain
            LogbookEntryError.Date -> R.string.entry_error_date
            LogbookEntryError.DepartureTime -> R.string.entry_error_departureTime
            LogbookEntryError.Departure -> R.string.entry_error_departure_location
            LogbookEntryError.HoldersOperatingCapacity -> R.string.entry_error_holders_operating_capacity
            LogbookEntryError.LandingCount -> R.string.entry_error_landing_count
            LogbookEntryError.SecondsDay -> R.string.entry_error_seconds_day
            LogbookEntryError.SecondsNight -> R.string.entry_error_seconds_night
            LogbookEntryError.SecondsInstrument -> R.string.entry_error_seconds_instrument
            LogbookEntryError.SecondsInstrumentSim -> R.string.entry_error_seconds_instrument_sim
            LogbookEntryError.TakeOffCount -> R.string.entry_error_take_off_count
            LogbookEntryError.Arrival -> R.string.entry_error_arrival_location
            else -> return
        }

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.entry_error_dialog_title)
            .setMessage(errorResId)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun clearInputFocus() {
        captainEditText.clearFocus()
        operatingCapacityEditText.clearFocus()
    }
}
