package dev.mcd.pilotlog.ui.draftentry.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.destination.Destination
import dev.mcd.pilotlog.ui.draftentry.destination.AddDestinationViewModel.State.Dismiss
import dev.mcd.pilotlog.util.delegates.EditTextDelegate
import kotlinx.android.synthetic.main.add_destination_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddDestinationFragment : BottomSheetDialogFragment() {

    private var name by EditTextDelegate { nameEditText }
    private var icao by EditTextDelegate { icaoEditText }

    private val viewModel by viewModels<AddDestinationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_destination_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        nameEditText.addTextChangedListener {
            updateDestination()
        }
        icaoEditText.addTextChangedListener {
            updateDestination()
        }
        addButton.setOnClickListener {
            viewModel.onAddClicked()
        }
    }

    private fun setupVM() {
        viewModel.state
            .onEach(::handleState)
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: AddDestinationViewModel.State) {
        when (state) {
            Dismiss -> findNavController().navigateUp()
        }
    }

    private fun isValid(): Boolean {
        return name.isNotBlank()
    }

    private fun updateDestination() {
        if (isValid()) {
            val destination = Destination(name.trim(), icao.trim())
            viewModel.onDestinationUpdated(destination)
            addButton.isEnabled = true
        } else {
            addButton.isEnabled = false
        }
    }
}
