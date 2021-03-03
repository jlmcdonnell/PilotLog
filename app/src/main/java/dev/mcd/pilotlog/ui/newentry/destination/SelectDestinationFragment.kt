package dev.mcd.pilotlog.ui.newentry.destination

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
import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.ui.newentry.destination.SelectDestinationFragmentDirections.toAddDestination
import dev.mcd.pilotlog.ui.newentry.destination.SelectDestinationViewModel.State.Dismiss
import dev.mcd.pilotlog.ui.newentry.destination.SelectDestinationViewModel.State.ShowLoadError
import dev.mcd.pilotlog.util.extensions.showErrorToast
import kotlinx.android.synthetic.main.select_destination_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SelectDestinationFragment : BottomSheetDialogFragment() {

    private lateinit var adapter: GroupAdapter<*>

    private val args: SelectDestinationFragmentArgs by navArgs()
    private val viewModel by viewModels<SelectDestinationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_destination_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        setupDestinationList()
        newDestinationButton.setOnClickListener {
            navigateToAddDestination()
        }
    }

    private fun setupVM() {
        viewModel.params = args.params
        viewModel.state
            .onEach(::updateState)
            .launchIn(lifecycleScope)

        viewModel.destinations
            .onEach(::displayDestinations)
            .launchIn(lifecycleScope)
    }

    private fun setupDestinationList() {
        adapter = GroupAdapter<GroupieViewHolder>()
        adapter.setOnItemClickListener { item, _ ->
            viewModel.onDestinationSelected((item as DestinationListItem).destination)
        }

        destinationListView.layoutManager = LinearLayoutManager(context)
        destinationListView.adapter = adapter
    }

    private fun navigateToAddDestination() {
        findNavController().navigate(toAddDestination())
    }

    private fun updateState(state: SelectDestinationViewModel.State) {
        when (state) {
            is Dismiss -> findNavController().navigateUp()
            is ShowLoadError -> requireContext().showErrorToast()
        }
    }

    private fun displayDestinations(destinations: List<Destination>) {
        adapter.clear()
        adapter.addAll(destinations.map { DestinationListItem(it) })
    }
}
