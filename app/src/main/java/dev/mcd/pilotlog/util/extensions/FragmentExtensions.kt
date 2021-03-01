package dev.mcd.pilotlog.util.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import timber.log.Timber

inline fun <reified T> Fragment.getNavigationResultLiveData(key: String = T::class.qualifiedName!!): LiveData<T>? {
    Timber.d("key=$key")
    return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(key)
}

inline fun <reified T> Fragment.setNavigationResult(result: T, key: String = T::class.qualifiedName!!) {
    Timber.d("key=$key")
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}
