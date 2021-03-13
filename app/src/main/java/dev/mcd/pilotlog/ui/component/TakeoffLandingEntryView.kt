package dev.mcd.pilotlog.ui.component

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.util.delegates.EditTextDelegate
import kotlinx.android.synthetic.main.takeoff_landing_entry_view.view.*

class TakeoffLandingEntryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onTakeOffLandingCountChanged: ((takeOffs: Int, landings: Int) -> Unit)? = null

    private var takeOffs by EditTextDelegate { takeOffsEditText }
    private var landings by EditTextDelegate { landingsEditText }

    init {
        inflate(context, R.layout.takeoff_landing_entry_view, this)

        val onUpdate = {
            val takeOffs = takeOffs.toIntOrNull() ?: 0
            val landings = landings.toIntOrNull() ?: 0
            onTakeOffLandingCountChanged?.invoke(takeOffs, landings)
        }

        takeOffsEditText.addTextChangedListener { onUpdate() }
        landingsEditText.addTextChangedListener { onUpdate() }
    }
}

