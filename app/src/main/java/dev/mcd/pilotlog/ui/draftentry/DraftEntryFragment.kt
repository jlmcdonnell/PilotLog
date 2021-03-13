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
import dev.mcd.pilotlog.ui.draftentry.DraftEntryFragmentDirections.*
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
        operatingCapacityPreset1.setOnClickListener {
            viewModel.onOperatingCapacityChanged(operatingCapacityPreset1.text.toString())
        }
        operatingCapacityPreset2.setOnClickListener {
            viewModel.onOperatingCapacityChanged(operatingCapacityPreset2.text.toString())
        }
        departureLocation.onSelectLocationClicked = {
            viewModel.onSelectDepartureClicked()
        }
        arrivalLocation.onSelectLocationClicked = {
            viewModel.onSelectArrivalClicked()
        }
        aircraftView.setOnClickListener {
            viewModel.onSelectAircraftClicked()
        }
        dateView.setOnClickListener {
            viewModel.onSelectDateClicked()
        }
        saveButton.setOnClickListener {
            viewModel.onSaveClicked()
        }
        takeOffLandingEntryView.onTakeOffLandingCountChanged = { takeOffs, landings ->
            viewModel.onTakeOffLandingCountUpdated(takeOffs, landings)
        }
        flyingTimeView.onUpdateFlyingTimes = {
            viewModel.onUpdateFlyingTimesClicked()
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

    private fun handleState(state: State) {
        when (state) {
            is State.Dismiss -> findNavController().navigateUp()
            is State.SaveError -> displayLogEntryError(state.logbookEntryError)
            is State.SelectAircraft -> selectAircraft()
            is State.SelectArrival -> selectArrival()
            is State.SelectDate -> selectDate()
            is State.SelectDeparture -> selectDeparture()
            is State.UpdateFlyingTimes -> updateFlyingTimes()
        }
    }

    private fun selectDeparture() {
        clearInputFocus()
        findNavController().navigate(toSelectLocation(SelectLocationParams.ModeDeparture))
    }

    private fun selectArrival() {
        clearInputFocus()
        findNavController().navigate(toSelectLocation(SelectLocationParams.ModeArrival))
    }

    private fun selectAircraft() {
        clearInputFocus()
        findNavController().navigate(toSelectAircraft())
    }

    private fun updateFlyingTimes() {
        clearInputFocus()
        findNavController().navigate(toFlyingTimeEntry())
    }

    private fun selectDate() {
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

        flyingTimeView.setEntry(logbookEntry)
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

    private fun displayLogEntryError(error: LogbookEntryError) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.entry_error_dialog_title)
            .setMessage(error.errorStringRes)
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
