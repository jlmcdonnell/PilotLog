package dev.mcd.pilotlog.util.extensions

import android.content.Context
import android.widget.Toast
import dev.mcd.pilotlog.R

fun Context.showErrorToast() {
    Toast.makeText(this, R.string.generic_error, Toast.LENGTH_SHORT).show()
}
