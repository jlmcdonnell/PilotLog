package dev.mcd.pilotlog.ui.draftentry.flyingtime

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import dev.mcd.pilotlog.R
import dev.mcd.pilotlog.domain.aircraft.EngineType
import kotlinx.android.synthetic.main.flying_time_item_view.*

class FlyingTimeItemView(
    private val type: String,
    private val time: Int,
    private val engineType: EngineType,
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            val context = root.context
            typeText.text = type

            //noinspection SetTextI18n
            timeText.text = "%.2f".format(time / 3600f)
            if (engineType == EngineType.Multi) {
                engineTypeChip.text = context.getString(R.string.engine_type_multi)
            } else {
                engineTypeChip.text = context.getString(R.string.engine_type_single)
            }
        }
    }

    override fun getLayout(): Int = R.layout.flying_time_item_view
}
