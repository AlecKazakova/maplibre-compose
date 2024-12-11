package ca.derekellis.maplibre

import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.findViewTreeLifecycleOwner
import ca.derekellis.maplibre.compose.applySources
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.ImageRequest
import coil3.toBitmap
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style

@Composable
public fun MapLibreMap(
  modifier: Modifier = Modifier,
  style: String? = null,
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

  LaunchedEffect(mapRef, styleRef) {
    if (mapRef != null && styleRef != null) {
      applySources(mapRef!!, styleRef!!, content)
    }
  }

  LaunchedEffect(mapRef, style) {
    val map = mapRef ?: return@LaunchedEffect

    map.setStyle(style)
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

  val context = LocalContext.current
  val imageLoader = remember {
    ImageLoader.Builder(context)
      .components {
        add(
          OkHttpNetworkFetcherFactory(
            callFactory = { OkHttpClient() }
          )
        )
      }
      .build()
  }
  val scope = rememberCoroutineScope()

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapView(context).also { view ->
        view.getMapAsync { map ->
          mapRef = map
          state.bindMap(map)
        }
        view.addOnStyleImageMissingListener { imageId ->
          // Set a default placeholder
          styleRef?.addImageAsync(imageId, BitmapFactory.decodeResource(context.resources, R.drawable.default_icon))

          scope.launch(start = CoroutineStart.UNDISPATCHED) {
            val request = ImageRequest.Builder(context)
              // The image ID is a regular string, but since I'm using URLs here I can just pass them in
              // we should implement a way to filter out non-image URIs before trying to load them via Coil
              // e.g. with a custom protocol which we can map to http(s) before trying to load it
              .data(imageId)
              .build()

            val result = imageLoader.execute(request)
            val bitmap = result.image?.toBitmap() ?: return@launch

            bitmap.density = context.resources.displayMetrics.densityDpi
            styleRef?.addImageAsync(imageId, bitmap)
          }
          Log.w("StyleImageMissing", "Missing style image! '$imageId'")
        }
      }.apply { manageLifecycle() }
    },
    update = { _ -> },
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
