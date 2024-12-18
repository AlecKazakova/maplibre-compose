package ca.derekellis.maplibre

import android.view.View
import android.view.View.OnAttachStateChangeListener
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.findViewTreeLifecycleOwner
import ca.derekellis.maplibre.compose.applySources
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style

private sealed interface StyleWrapper {
  data class Uri(val uri: String) : StyleWrapper
  data class Builder(val builder: Style.Builder) : StyleWrapper
}

@Composable
private fun MapLibreMapImpl(
  modifier: Modifier = Modifier,
  style: StyleWrapper? = null,
  state: MapState = rememberMapState(),
  contentPadding: PaddingValues = WindowInsets.safeDrawing.asPaddingValues(),
  logoPadding: PaddingValues = computeLogoPadding(contentPadding),
  attributionPadding: PaddingValues = computeAttributionPadding(logoPadding),
  compassPadding: PaddingValues = computeCompassPadding(contentPadding),
  content: @Composable MapScope.() -> Unit = {},
) {
  val density = LocalDensity.current
  val layoutDirection = LocalLayoutDirection.current

  var mapRef: MapLibreMap? by remember { mutableStateOf(null) }
  var styleRef: Style? by remember { mutableStateOf(null) }
  var mapViewCallbacksRef: MapViewCallbacks? by remember { mutableStateOf(null) }

  LaunchedEffect(mapRef, styleRef, mapViewCallbacksRef) {
    if (mapRef != null && styleRef != null && mapViewCallbacksRef != null) {
      applySources(mapRef!!, styleRef!!, mapViewCallbacksRef!!, content)
    }
  }

  LaunchedEffect(mapRef, style) {
    val map = mapRef ?: return@LaunchedEffect

    when (style) {
      is StyleWrapper.Builder -> map.setStyle(style.builder)
      is StyleWrapper.Uri -> map.setStyle(style.uri)
      else -> {}
    }
    map.getStyle { styleRef = it }
  }

  LaunchedEffect(mapRef, logoPadding, attributionPadding, compassPadding) {
    val map = mapRef ?: return@LaunchedEffect

    with(density) {
      map.uiSettings.setLogoMargins(
        logoPadding.calculateLeftPadding(layoutDirection).roundToPx(),
        logoPadding.calculateTopPadding().roundToPx(),
        logoPadding.calculateRightPadding(layoutDirection).roundToPx(),
        logoPadding.calculateBottomPadding().roundToPx(),
      )

      map.uiSettings.setAttributionMargins(
        attributionPadding.calculateLeftPadding(layoutDirection).roundToPx(),
        attributionPadding.calculateTopPadding().roundToPx(),
        attributionPadding.calculateRightPadding(layoutDirection).roundToPx(),
        attributionPadding.calculateBottomPadding().roundToPx(),
      )

      map.uiSettings.setCompassMargins(
        compassPadding.calculateLeftPadding(layoutDirection).roundToPx(),
        compassPadding.calculateTopPadding().roundToPx(),
        compassPadding.calculateRightPadding(layoutDirection).roundToPx(),
        compassPadding.calculateBottomPadding().roundToPx(),
      )
    }
  }

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapView(context).also { view ->
        view.getMapAsync { map ->
          mapRef = map
          state.bindMap(map)
        }

        mapViewCallbacksRef = MapViewCallbacksImpl(view)
      }.apply { manageLifecycle() }
    },
    update = { _ -> },
  )
}

@Composable
public fun MapLibreMap(
  modifier: Modifier = Modifier,
  style: String,
  state: MapState = rememberMapState(),
  contentPadding: PaddingValues = WindowInsets.safeDrawing.asPaddingValues(),
  logoPadding: PaddingValues = computeLogoPadding(contentPadding),
  attributionPadding: PaddingValues = computeAttributionPadding(logoPadding),
  compassPadding: PaddingValues = computeCompassPadding(contentPadding),
  content: @Composable MapScope.() -> Unit = {},
) {
  MapLibreMapImpl(
    modifier = modifier,
    style = StyleWrapper.Uri(style),
    state = state,
    contentPadding = contentPadding,
    logoPadding = logoPadding,
    attributionPadding = attributionPadding,
    compassPadding = compassPadding,
    content = content,
  )
}

@Composable
public fun MapLibreMap(
  modifier: Modifier = Modifier,
  style: Style.Builder? = null,
  state: MapState = rememberMapState(),
  contentPadding: PaddingValues = WindowInsets.safeDrawing.asPaddingValues(),
  logoPadding: PaddingValues = computeLogoPadding(contentPadding),
  attributionPadding: PaddingValues = computeAttributionPadding(logoPadding),
  compassPadding: PaddingValues = computeCompassPadding(contentPadding),
  content: @Composable MapScope.() -> Unit = {},
) {
  MapLibreMapImpl(
    modifier = modifier,
    style = style?.let { StyleWrapper.Builder(it) },
    state = state,
    contentPadding = contentPadding,
    logoPadding = logoPadding,
    attributionPadding = attributionPadding,
    compassPadding = compassPadding,
    content = content,
  )
}

private fun MapView.manageLifecycle() {
  val observer = LifecycleEventObserver { _, event ->
    when (event) {
      Lifecycle.Event.ON_CREATE -> onCreate(null)
      Lifecycle.Event.ON_START -> onStart()
      Lifecycle.Event.ON_RESUME -> onResume()
      Lifecycle.Event.ON_PAUSE -> onPause()
      Lifecycle.Event.ON_STOP -> onStop()
      Lifecycle.Event.ON_DESTROY -> onDestroy()
      Lifecycle.Event.ON_ANY -> {
        /* No-op */
      }
    }
  }

  addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(v: View) {
      val owner = requireNotNull(v.findViewTreeLifecycleOwner())
      owner.lifecycle.addObserver(observer)
    }

    override fun onViewDetachedFromWindow(v: View) {
      val owner = requireNotNull(v.findViewTreeLifecycleOwner())
      owner.lifecycle.removeObserver(observer)
    }
  })
}

@Composable
public fun computeLogoPadding(contentPadding: PaddingValues): PaddingValues {
  return PaddingValues(
    start = 4.dp + contentPadding.calculateStartPadding(LocalLayoutDirection.current),
    top = 4.dp + contentPadding.calculateTopPadding(),
    end = 4.dp + contentPadding.calculateEndPadding(LocalLayoutDirection.current),
    bottom = 4.dp + contentPadding.calculateBottomPadding(),
  )
}

@Composable
public fun computeAttributionPadding(logoPadding: PaddingValues): PaddingValues {
  return PaddingValues(
    start = 92.dp + logoPadding.calculateStartPadding(LocalLayoutDirection.current),
    top = 4.dp + logoPadding.calculateTopPadding(),
    end = 4.dp + logoPadding.calculateEndPadding(LocalLayoutDirection.current),
    bottom = 4.dp + logoPadding.calculateBottomPadding(),
  )
}

@Composable
public fun computeCompassPadding(contentPadding: PaddingValues): PaddingValues {
  return PaddingValues(
    start = 4.dp + contentPadding.calculateStartPadding(LocalLayoutDirection.current),
    top = 4.dp + contentPadding.calculateTopPadding(),
    end = 4.dp + contentPadding.calculateEndPadding(LocalLayoutDirection.current),
    bottom = 4.dp + contentPadding.calculateBottomPadding(),
  )
}

private class MapViewCallbacksImpl(private val mapView: MapView) : MapViewCallbacks {
  override fun addOnStyleImageMissingListener(listener: MapView.OnStyleImageMissingListener) {
    mapView.addOnStyleImageMissingListener(listener)
  }

  override fun removeOnStyleImageMissingListener(listener: MapView.OnStyleImageMissingListener) {
    mapView.removeOnStyleImageMissingListener(listener)
  }
}
