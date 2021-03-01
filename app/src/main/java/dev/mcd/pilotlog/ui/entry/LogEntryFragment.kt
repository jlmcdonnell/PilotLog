package dev.mcd.pilotlog.ui.entry

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.databinding.LogEntryFragmentBinding
import dev.mcd.pilotlog.ui.destination.SelectDestinationFragment
import dev.mcd.pilotlog.ui.destination.SelectDestinationParams
import dev.mcd.pilotlog.ui.entry.LogEntryFragment.DestinationPurpose.From
import dev.mcd.pilotlog.ui.entry.LogEntryFragment.DestinationPurpose.To
import dev.mcd.pilotlog.util.extensions.getNavigationResultLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogEntryFragment : Fragment(R.layout.log_entry_fragment) {

    private lateinit var binding: LogEntryFragmentBinding
    private val viewModel by hiltNavGraphViewModels<LogEntryViewModel>(R.id.main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LogEntryFragmentBinding.bind(view)
        setupUI()
        setupViewModel()
    }

    private fun setupUI() {
        with(binding) {
            fromDestination.onSelectDestinationClicked = {
                showSelectDestination(From)
            }
            toDestination.onSelectDestinationClicked = {
                showSelectDestination(To)
            }
            dateButton.setOnClickListener {

            }
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            with(viewModel) {
                launch {
                    fromDestination.collect { binding.fromDestination.setDestination(it) }
                }
                launch {
                    toDestination.collect { binding.toDestination.setDestination(it) }
                }
                launch {
                    entryDate.collect { binding.dateButton.text = it }
                }
                start()
            }
        }
    }

    private fun showSelectDestination(destinationPurpose: DestinationPurpose) {
        val params = SelectDestinationParams(destinationPurpose.name)
        findNavController().navigate(LogEntryFragmentDirections.logEntryToSelectDestination(params))
        observeSelectDestinationResult()
    }

    private fun observeSelectDestinationResult() {
        getNavigationResultLiveData<SelectDestinationFragment.Result>()?.observe(viewLifecycleOwner) {
            when (it.tag) {
                From.name -> viewModel.onFromDestinationSelected(it.destination)
                To.name -> viewModel.onToDestinationSelected(it.destination)
            }
        }
    }

    private enum class DestinationPurpose {
        From, To
    }
}
