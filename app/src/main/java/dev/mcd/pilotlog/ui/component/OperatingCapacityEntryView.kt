package dev.mcd.pilotlog.ui.component

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import dev.mcd.pilotlog.R

class OperatingCapacityEntryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onOperatingCapacityChanged: ((String) -> Unit)? = null

    init {
        inflate(context, R.layout.operating_capacity_layout, this)
    }
}
