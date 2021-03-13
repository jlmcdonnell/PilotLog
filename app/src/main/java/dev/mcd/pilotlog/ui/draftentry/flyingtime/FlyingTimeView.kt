package dev.mcd.pilotlog.ui.draftentry.flyingtime

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.aircraft.isValid
import dev.mcd.pilotlog.domain.logbook.LogbookEntry
import kotlinx.android.synthetic.main.flying_time_view.view.*

class FlyingTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    var onUpdateFlyingTimes: (() -> Unit)? = null

    init {
        inflate(context, R.layout.flying_time_view, this)

        flyingTimesList.adapter = adapter
        flyingTimesList.layoutManager = LinearLayoutManager(context)

        flyingTimesList.setOnClickListener {
            onUpdateFlyingTimes?.invoke()
        }
        updateTimes.setOnClickListener {
            onUpdateFlyingTimes?.invoke()
        }
    }

    fun setEntry(entry: LogbookEntry) {
        val aircraft = entry.aircraft.takeIf { it.isValid } ?: return

        adapter.clear()
        val engineType = aircraft.engineType

        if (entry.secondsDay > 0) {
            val view = FlyingTimeItemView(
                type = context.getString(R.string.flying_time_day),
                time = entry.secondsDay,
                engineType = engineType
            )
            adapter.add(view)
        }

        if (entry.secondsNight > 0) {
            val view = FlyingTimeItemView(
                type = context.getString(R.string.flying_time_night),
                time = entry.secondsNight,
                engineType = engineType
            )
            adapter.add(view)
        }

        if (entry.secondsInstrument > 0) {
            val view = FlyingTimeItemView(
                type = context.getString(R.string.flying_time_instrument),
                time = entry.secondsInstrument,
                engineType = engineType
            )
            adapter.add(view)
        }

        if (entry.secondsInstrumentSim > 0) {
            val view = FlyingTimeItemView(
                type = context.getString(R.string.flying_time_instrument_sim),
                time = entry.secondsInstrumentSim,
                engineType = engineType
            )
            adapter.add(view)
        }

        if (adapter.itemCount > 0) {
            flyingTimesList.isVisible = true
            updateTimes.isVisible = false
        } else {
            flyingTimesList.isVisible = false
            updateTimes.isVisible = true
        }
    }
}
