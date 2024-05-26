# Compose Android Permissions

**Compose Android Permissions** is a library for easily handling permission requests in Android applications developed with Jetpack Compose.

## Installation

Add the dependency to your `build.gradle` file:

```groovy
implementation "com.piroworkz:compose-android-permissions:1.0.0"
```

## Usage

### Initial Setup

```kotlin
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.MutableState

// Create an instance of Permissions in your Composable
val permissions = rememberPermissionsState()
```

### Requesting Permissions

```kotlin
PermissionLaunchedEffect(
    permissionState = permissions,
    permissions = android.Manifest.permission.ACCESS_FINE_LOCATION, // List of required permissions
    onGranted = {
        // Actions to perform when permissions are granted
    }
)
```

### Handling Results Example

```kotlin

// If requesting location permitions on background according to documentatiuon you have to request foreground location first for android 10 and higher
@Composable
fun PermissionsScreen(
    onGranted: () -> Unit
) {

    val foregroundPermissions = rememberPermissionsState() // foreground permissions state
    val backgroundPermissions = rememberPermissionsState() // background permissions state

    val versionIsHigherOrEqualToQ =
        remember { mutableStateOf(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) } // sdk version  check

    // show foreground rationale
    val showForegroundDialog: Boolean =
        !foregroundPermissions.requestedAtLeastOnce && foregroundPermissions.state == SHOULD_SHOW_RATIONALE
    // show background rationale
    val showBackgroundDialog: Boolean =
        !backgroundPermissions.requestedAtLeastOnce && backgroundPermissions.state == SHOULD_SHOW_RATIONALE

    PermissionLaunchedEffect(
        permissionState = foregroundPermissions,
        ACCESS_FINE_LOCATION,
        ACCESS_COARSE_LOCATION,
        onGranted = {
            // HANDLE RESULT ON GRANTED
            if (!versionIsHigherOrEqualToQ.value) {
                onGranted()
            }
        },
    )

    @SuppressLint("InlinedApi")
    if (foregroundPermissions.state == GRANTED && versionIsHigherOrEqualToQ.value) {
        PermissionLaunchedEffect(
            permissionState = backgroundPermissions,
            ACCESS_BACKGROUND_LOCATION,
            onGranted = {
                // HANDLE RESULT ON GRANTED
                onGranted()
            }
        )
    }
    // Show Rationale
    if (showForegroundDialog || showBackgroundDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            WarningDialogView(
                title = "Permission Required",
                message = "This permission is required to access the app"
            ) {
                if (showForegroundDialog) {
                    foregroundPermissions.onDismissRationale()
                } else {
                    backgroundPermissions.onDismissRationale()
                }
            }
        }

    }
}

```

## License

Compose Android Permissions is licensed under the [MIT License](LICENSE).

## Contributing

Contributions are welcome. To contribute, follow these steps:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Make your changes
4. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

## Contact

Created by [David Luna](https://github.com/piroworkz) - Contact me!
