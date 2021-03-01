package dev.mcd.pilotlog.util.delegates

import android.widget.EditText
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class EditTextDelegate(editText: () -> EditText) {
    private val editText: EditText by lazy(editText)

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
        return editText.text.toString()
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
        editText.setText(value)
    }
}