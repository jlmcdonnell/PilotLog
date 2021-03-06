package dev.mcd.pilotlog.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.captain.Captain
import dev.mcd.pilotlog.domain.captain.CaptainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class CaptainEntryTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.autoCompleteTextViewStyle,
) : MaterialAutoCompleteTextView(context, attrs, defStyleAttr) {

    var onCaptainUpdated: ((String) -> Unit)? = null

    @Inject
    lateinit var captainRepository: CaptainRepository

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isInEditMode) {
            setupTextWatcher()
            getAutocompleteResults()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope.cancel()
    }

    private fun getAutocompleteResults() {
        captainRepository.get()
            .map { it.map(Captain::name) }
            .flowOn(Dispatchers.IO)
            .onEach(::updateAdapter)
            .take(1)
            .launchIn(scope)
    }

    private fun setupTextWatcher() {
        addTextChangedListener(
            afterTextChanged = {
                val text = it.toString()
                onCaptainUpdated?.invoke(text)
            }
        )
    }

    private fun updateAdapter(results: List<String>) {
        setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, results.toTypedArray()))
    }
}
