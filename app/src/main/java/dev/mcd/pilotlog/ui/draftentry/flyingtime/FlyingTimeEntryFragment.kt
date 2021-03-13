package dev.mcd.pilotlog.ui.draftentry.flyingtime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.ui.draftentry.flyingtime.FlyingTimeEntryViewModel.State
import dev.mcd.pilotlog.util.extensions.showErrorToast
import kotlinx.android.synthetic.main.flying_time_entry_fragment.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FlyingTimeEntryFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<FlyingTimeEntryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.flying_time_entry_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }

    private fun setupVM() {
        viewModel.state
            .onEach(::handleState)
            .launchIn(lifecycleScope)
    }

    private fun setupUI() {
        dayEntryView.onDurationChanged = {
            viewModel.onDaySecondsUpdated(it)
        }
        nightEntryView.onDurationChanged = {
            viewModel.onNightSecondsUpdated(it)
        }
        instrumentEntryView.onDurationChanged = {
            viewModel.onInstrumentSecondsUpdated(it)
        }
        instrumentSimEntryView.onDurationChanged = {
            viewModel.onInstrumentSimulatedSecondsUpdated(it)
        }
        saveButton.setOnClickListener {
            viewModel.onSaveClicked()
        }
    }

    private fun handleState(state: State) {
        when (state) {
            State.Dismiss -> findNavController().navigateUp()
            State.ShowError -> requireContext().showErrorToast()
        }
    }
}
