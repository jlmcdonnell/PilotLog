package dev.mcd.pilotlog.ui.newentry.aircraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.aircraft.Aircraft
import dev.mcd.pilotlog.ui.newentry.aircraft.SelectAircraftFragmentDirections.toAddAircraft
import dev.mcd.pilotlog.ui.newentry.aircraft.SelectAircraftViewModel.State
import dev.mcd.pilotlog.ui.newentry.aircraft.SelectAircraftViewModel.State.*
import dev.mcd.pilotlog.util.extensions.showErrorToast
import kotlinx.android.synthetic.main.select_aircraft_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SelectAircraftFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<SelectAircraftViewModel>()

    private lateinit var adapter: GroupAdapter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_aircraft_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        setupAircraftList()
        newAircraftButton.setOnClickListener {
            viewModel.onNewAircraftClicked()
        }
    }

    private fun setupVM() {
        viewModel.state
            .onEach(::updateState)
            .launchIn(lifecycleScope)

        viewModel.aircrafts
            .onEach(::displayAircrafts)
            .launchIn(lifecycleScope)
    }

    private fun setupAircraftList() {
        adapter = GroupAdapter<GroupieViewHolder>()
        adapter.setOnItemClickListener { item, _ ->
            viewModel.onAircraftSelected((item as AircraftListItem).aircraft)
        }
        aircraftListView.layoutManager = LinearLayoutManager(context)
        aircraftListView.adapter = adapter
    }

    private fun displayAircrafts(aircrafts: List<Aircraft>) {
        adapter.clear()
        adapter.addAll(aircrafts.map(::AircraftListItem))
    }

    private fun updateState(state: State) {
        when (state) {
            Dismiss -> findNavController().navigateUp()
            NavigateAddAircraft -> findNavController().navigate(toAddAircraft())
            ShowLoadError -> requireContext().showErrorToast()
            ShowUpdateError -> requireContext().showErrorToast()
        }
    }
}
