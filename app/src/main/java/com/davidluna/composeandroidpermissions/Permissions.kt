package com.davidluna.composeandroidpermissions

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.MutableState

class Permissions(
    private val _state: MutableState<PermissionsState>,
    private val launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    private val _requested: MutableState<Boolean>,
) {

    val requestedAtLeastOnce: Boolean get() = _requested.value
    val state: PermissionsState get() = _state.value

    fun launchRequestFor(vararg permissions: String) {
        launcher.launch(arrayOf(*permissions))
    }

    fun onDismissRationale() {
        _requested.value = true
        _state.value = PermissionsState.DENIED
    }
}