package com.davidluna.composeandroidpermissions

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberPermissionsState(
    state: MutableState<PermissionsState> = remember { mutableStateOf(PermissionsState.DENIED) },
    requestedAtLeastOnce: MutableState<Boolean> = remember { mutableStateOf(false) },
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { results ->
        if (results.all { it.value }) {
            state.value = PermissionsState.GRANTED
        } else {
            state.value = PermissionsState.SHOULD_SHOW_RATIONALE
        }
    }
) = remember {
    Permissions(
        _state = state,
        launcher = launcher,
        _requested = requestedAtLeastOnce
    )
}