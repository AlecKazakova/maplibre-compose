package ca.derekellis.maplibre

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdate
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapLibreMap.OnCameraIdleListener
import kotlin.coroutines.resume
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

public class MapState(
  private var initialTarget: LatLng = LatLng(),
  private var initialZoom: Double = 0.0,
  private var initialBearing: Double = 0.0,
  private var initialTilt: Double = 0.0,
  private var initialPadding: DoubleArray = doubleArrayOf(0.0, 0.0, 0.0, 0.0),
) {
  private val mapState = MutableStateFlow<MapLibreMap?>(null)

  internal fun save(): Bundle {
    return Bundle().apply {
      mapState.value?.let { mapState ->
        putDouble("mapstate_lat", mapState.cameraPosition.target!!.latitude)
        putDouble("mapstate_long", mapState.cameraPosition.target!!.longitude)
        putDouble("mapstate_zoom", mapState.cameraPosition.zoom)
        putDouble("mapstate_bearing", mapState.cameraPosition.bearing)
        putDouble("mapstate_tilt", mapState.cameraPosition.tilt)
        putDoubleArray("mapstate_padding", mapState.cameraPosition.padding)
      }
    }
  }

  private suspend inline fun withMap(block: (MapLibreMap) -> Unit) {
    val map = mapState.value ?: mapState.filterNotNull().first()
    block(map)
  }

  internal fun bindMap(map: MapLibreMap) {
    check(this.mapState.value == null) {
      "Cannot rebind MapState after already being bound!"
    }

    mapState.value = map
    map.cameraPosition = CameraPosition.Builder()
      .target(initialTarget)
      .zoom(initialZoom)
      .bearing(initialBearing)
      .tilt(initialTilt)
      .padding(initialPadding)
      .build()
  }

  public var target: LatLng
    get() = mapState.value?.cameraPosition?.target ?: initialTarget
    set(value) {
      if (mapState.value != null) {
        mapState.value?.cameraPosition = CameraPosition.Builder().target(value).build()
      } else {
        initialTarget = value
      }
    }

  public var zoom: Double
    get() = mapState.value?.cameraPosition?.zoom ?: initialZoom
    set(value) {
      if (mapState.value != null) {
        mapState.value?.cameraPosition = CameraPosition.Builder().zoom(value).build()
      } else {
        initialZoom = value
      }
    }

  public var bearing: Double
    get() = mapState.value?.cameraPosition?.bearing ?: initialBearing
    set(value) {
      if (mapState.value != null) {
        mapState.value?.cameraPosition = CameraPosition.Builder().bearing(value).build()
      } else {
        initialBearing = value
      }
    }

  public var tilt: Double
    get() = mapState.value?.cameraPosition?.tilt ?: initialTilt
    set(value) {
      if (mapState.value != null) {
        mapState.value?.cameraPosition = CameraPosition.Builder().tilt(value).build()
      } else {
        initialTilt = value
      }
    }

  public suspend fun easeTo(
    target: LatLng = this.target,
    zoom: Double = this.zoom,
    bearing: Double = this.bearing,
    tilt: Double = this.tilt,
    duration: Duration = 300.milliseconds,
  ) {
    val cameraUpdate = object : CameraUpdate {
      override fun getCameraPosition(maplibreMap: MapLibreMap): CameraPosition =
        CameraPosition.Builder()
          .target(target)
          .zoom(zoom)
          .bearing(bearing)
          .tilt(tilt)
          .build()
    }

    easeTo(cameraUpdate, duration)
  }

  public suspend fun easeTo(
    cameraUpdate: CameraUpdate,
    duration: Duration = 300.milliseconds,
  ): Unit = withMap { map ->
    doAnimation(map) {
      map.easeCamera(cameraUpdate, duration.inWholeMilliseconds.toInt())
    }
  }

  private suspend fun doAnimation(map: MapLibreMap, block: () -> Unit) {
    var listener: OnCameraIdleListener
    var resumed = false

    suspendCancellableCoroutine { continuation ->
      block()
      listener = OnCameraIdleListener {
        if (!resumed) {
          continuation.resume(Unit)
        }
        resumed = true
      }

      map.addOnCameraIdleListener(listener)

      continuation.invokeOnCancellation {
        map.cancelTransitions()
        map.removeOnCameraIdleListener(listener)
      }
    }
  }
}

@Composable
public fun rememberMapState(
  target: LatLng = LatLng(),
  zoom: Double = 0.0,
  bearing: Double = 0.0,
  tilt: Double = 0.0,
  padding: PaddingValues = PaddingValues(0.dp),
): MapState {
  val paddingValues = LocalDensity.current.run {
    doubleArrayOf(
      padding.calculateLeftPadding(LayoutDirection.Ltr).toPx().toDouble(),
      padding.calculateTopPadding().toPx().toDouble(),
      padding.calculateRightPadding(LayoutDirection.Ltr).toPx().toDouble(),
      padding.calculateBottomPadding().toPx().toDouble(),
    )
  }

  return rememberSaveable(saver = Saver(
    save = { mapState -> mapState.save() },
    restore = { bundle: Bundle ->
        MapState(
          initialTarget = LatLng(bundle.getDouble("mapstate_lat", target.latitude), bundle.getDouble("mapstate_long", target.longitude)),
          initialZoom = bundle.getDouble("mapstate_zoom", zoom),
          initialBearing = bundle.getDouble("mapstate_bearing", bearing),
          initialTilt = bundle.getDouble("mapstate_tilt", tilt),
          initialPadding =
          if (bundle.containsKey("mapstate_padding")) bundle.getDoubleArray("mapstate_padding")!!
          else paddingValues
        )
    }
  )) { MapState(target, zoom, bearing, tilt, paddingValues) }
}
