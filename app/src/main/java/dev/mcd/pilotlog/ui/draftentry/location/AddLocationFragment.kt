package dev.mcd.pilotlog.ui.draftentry.location

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
import dev.mcd.pilotlog.domain.location.Location
import dev.mcd.pilotlog.ui.draftentry.location.AddLocationViewModel.State.Dismiss
import dev.mcd.pilotlog.util.delegates.EditTextDelegate
import kotlinx.android.synthetic.main.add_location_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddLocationFragment : BottomSheetDialogFragment() {

    private var name by EditTextDelegate { nameEditText }
    private var icao by EditTextDelegate { icaoEditText }

    private val viewModel by viewModels<AddLocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_location_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupUI() {
        nameEditText.addTextChangedListener {
            updateLocation()
        }
        icaoEditText.addTextChangedListener {
            updateLocation()
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

    private fun handleState(state: AddLocationViewModel.State) {
        when (state) {
            Dismiss -> findNavController().navigateUp()
        }
    }

    private fun isValid(): Boolean {
        return name.isNotBlank()
    }

    private fun updateLocation() {
        if (isValid()) {
            val location = Location(name.trim(), icao.trim())
            viewModel.onLocationUpdated(location)
            addButton.isEnabled = true
        } else {
            addButton.isEnabled = false
        }
    }
}
