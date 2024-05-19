package com.piroworkz.composeandroidpermissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PermissionLaunchedEffect(
    permissionState: Permissions,
    vararg permissions: String,
    onGranted: () -> Unit
) {
    LaunchedEffect(key1 = permissionState.state) {
        when (permissionState.state) {
            PermissionsState.GRANTED -> {
                onGranted()
            }

            PermissionsState.DENIED -> {
                permissionState.launchRequestFor(permissions = permissions)
            }

            else -> {
                return@LaunchedEffect
            }
        }
    }
}