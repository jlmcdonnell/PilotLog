package dev.mcd.pilotlog.ui.newentry

import android.os.Bundle
import android.view.View
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
import dev.mcd.pilotlog.domain.date.DateString
import dev.mcd.pilotlog.domain.entry.Entry
import dev.mcd.pilotlog.ui.newentry.NewEntryFragmentDirections.toSelectAircraft
import dev.mcd.pilotlog.ui.newentry.NewEntryFragmentDirections.toSelectDestination
import dev.mcd.pilotlog.ui.newentry.destination.SelectDestinationParams.ModeFrom
import dev.mcd.pilotlog.ui.newentry.destination.SelectDestinationParams.ModeTo
import kotlinx.android.synthetic.main.new_entry_fragment.*
import kotlinx.android.synthetic.main.operating_capacity_layout.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NewEntryFragment : Fragment(R.layout.new_entry_fragment) {

    private val viewModel by viewModels<NewEntryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        deleteButton.setOnClickListener {
            viewModel.onDeleteClicked()
        }
        captainEditText.onCaptainUpdated = { captain ->
            viewModel.onCaptainUpdated(captain)
        }
        operatingCapacityView.onOperatingCapacityChanged = {
            viewModel.onOperatingCapacityChanged(it)
        }
        fromDestination.onSelectDestinationClicked = {
            findNavController().navigate(toSelectDestination(ModeFrom))
        }
        toDestination.onSelectDestinationClicked = {
            findNavController().navigate(toSelectDestination(ModeTo))
        }
        aircraftView.setOnClickListener {
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
        with(departArrivalEntryView) {
            provideFragmentManager = { childFragmentManager }
            onTimesUpdated = { depart, arrive -> }
        }
    }

    private fun setupVM() {
        viewModel.newEntries
            .onEach(::displayEntry)
            .launchIn(lifecycleScope)
    }

    private fun showDatePicker() {
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

    private fun displayEntry(entry: Entry) {
        captainEditText.setText(entry.captain)
        captainEditText.setSelection(captainEditText.length())
        operatingCapacityEditText.setText(entry.holdersOperatingCapacity)
        fromDestination.setDestination(entry.fromDestination)
        toDestination.setDestination(entry.toDestination)
        displayEntryDate(entry.date)
        displayAircraft(entry.aircraft)
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
}
