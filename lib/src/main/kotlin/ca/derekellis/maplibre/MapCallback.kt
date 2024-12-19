package ca.derekellis.maplibre

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import org.maplibre.android.maps.MapLibreMap.OnCameraIdleListener
import org.maplibre.android.maps.MapLibreMap.OnCameraMoveCanceledListener
import org.maplibre.android.maps.MapLibreMap.OnCameraMoveListener
import org.maplibre.android.maps.MapLibreMap.OnMapClickListener
import org.maplibre.android.maps.MapLibreMap.OnScaleListener
import org.maplibre.android.maps.MapView.OnStyleImageMissingListener

@Composable
public fun MapScope.OnCameraIdle(listener: OnCameraIdleListener) {
  DisposableEffect(listener) {
    map.addOnCameraIdleListener(listener)

    onDispose {
      map.removeOnCameraIdleListener(listener)
    }
  }
}

@Composable
public fun MapScope.OnCameraMove(listener: OnCameraMoveListener) {
  DisposableEffect(listener) {
    map.addOnCameraMoveListener(listener)

    onDispose {
      map.removeOnCameraMoveListener(listener)
    }
  }
}

@Composable
public fun MapScope.OnCameraMoveCanceled(listener: OnCameraMoveCanceledListener) {
  DisposableEffect(listener) {
    map.addOnCameraMoveCancelListener(listener)

    onDispose {
      map.removeOnCameraMoveCancelListener(listener)
    }
  }
}

@Composable
public fun MapScope.OnMapClick(listener: OnMapClickListener) {
  DisposableEffect(listener) {
    map.addOnMapClickListener(listener)

    onDispose {
      map.removeOnMapClickListener(listener)
    }
  }
}

@Composable
public fun MapScope.OnStyleImageMissing(listener: OnStyleImageMissingListener) {
  DisposableEffect(listener) {
    mapViewCallbacks.addOnStyleImageMissingListener(listener)

    onDispose {
      mapViewCallbacks.removeOnStyleImageMissingListener(listener)
    }
  }
}
