package dev.mcd.pilotlog.ui.draftentry.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.location.Location
import dev.mcd.pilotlog.ui.draftentry.location.SelectLocationFragmentDirections.toAddLocation
import dev.mcd.pilotlog.ui.draftentry.location.SelectLocationViewModel.State.Dismiss
import dev.mcd.pilotlog.ui.draftentry.location.SelectLocationViewModel.State.ShowLoadError
import dev.mcd.pilotlog.util.extensions.showErrorToast
import kotlinx.android.synthetic.main.select_location_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SelectLocationFragment : BottomSheetDialogFragment() {

    private lateinit var adapter: GroupAdapter<*>

    private val args: SelectLocationFragmentArgs by navArgs()
    private val viewModel by viewModels<SelectLocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_location_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        setupLocationList()
        newLocationButton.setOnClickListener {
            navigateToAddLocation()
        }
    }

    private fun setupVM() {
        viewModel.params = args.params
        viewModel.state
            .onEach(::updateState)
            .launchIn(lifecycleScope)

        viewModel.locations
            .onEach(::displayLocations)
            .launchIn(lifecycleScope)
    }

    private fun setupLocationList() {
        adapter = GroupAdapter<GroupieViewHolder>()
        adapter.setOnItemClickListener { item, _ ->
            viewModel.onLocationSelected((item as LocationListItem).location)
        }

        locationListView.layoutManager = LinearLayoutManager(context)
        locationListView.adapter = adapter
    }

    private fun navigateToAddLocation() {
        findNavController().navigate(toAddLocation())
    }

    private fun updateState(state: SelectLocationViewModel.State) {
        when (state) {
            is Dismiss -> findNavController().navigateUp()
            is ShowLoadError -> requireContext().showErrorToast()
        }
    }

    private fun displayLocations(locations: List<Location>) {
        adapter.clear()
        adapter.addAll(locations.map { LocationListItem(it) })
    }
}
