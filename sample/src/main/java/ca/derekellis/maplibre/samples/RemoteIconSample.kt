package ca.derekellis.maplibre.samples

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ca.derekellis.maplibre.DemoStyle
import ca.derekellis.maplibre.MapLibreMap
import ca.derekellis.maplibre.Navigator
import ca.derekellis.maplibre.OnStyleImageMissing
import ca.derekellis.maplibre.R
import ca.derekellis.maplibre.Screen
import ca.derekellis.maplibre.layers.CircleLayer
import ca.derekellis.maplibre.layers.SymbolLayer
import ca.derekellis.maplibre.rememberMapState
import ca.derekellis.maplibre.sources.GeoJsonSource
import ca.derekellis.maplibre.styles.circleColor
import ca.derekellis.maplibre.styles.circleRadius
import ca.derekellis.maplibre.styles.iconImage
import ca.derekellis.maplibre.styles.iconSize
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.ImageRequest
import coil3.toBitmap
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.maplibre.android.style.expressions.Expression

@Composable
fun RemoteIconSample(navigator: Navigator) {
  val scope = rememberCoroutineScope()
  val context = LocalContext.current

  val imageLoader = remember {
    ImageLoader.Builder(context)
      .components {
        add(
          OkHttpNetworkFetcherFactory(
            callFactory = { OkHttpClient() },
          ),
        )
      }
      .build()
  }

  val defaultIconBitmap =
    remember { BitmapFactory.decodeResource(context.resources, R.drawable.default_icon) }

  Scaffold(
    topBar = { SampleAppBar(title = "Remote Icons", onNavigate = { navigator.goTo(Screen.Home) }) },
  ) { innerPadding ->
    val mapState = rememberMapState(padding = innerPadding)

    Column(modifier = Modifier.consumeWindowInsets(innerPadding)) {
      MapLibreMap(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f),
        style = DemoStyle.Default.url,
        state = mapState,
        contentPadding = innerPadding,
        logoPadding = PaddingValues(4.dp),
      ) {
        OnStyleImageMissing { imageId ->
          // Set a default placeholder
          style.addImage(imageId, defaultIconBitmap)

          scope.launch(start = UNDISPATCHED) {
            val request = ImageRequest.Builder(context)
              // The imageId is just a URL for the purposes of this sample
              .data(imageId)
              .build()
            val result = imageLoader.execute(request)

            val bitmap = result.image?.toBitmap() ?: return@launch
            bitmap.density = context.resources.displayMetrics.densityDpi

            style.addImageAsync(imageId, bitmap)
          }
        }

        GeoJsonSource(id = "data", geojson = TEST_DATA) {
          CircleLayer(id = "circles") {
            circleColor(Color.White)
            circleRadius(25f)
          }
          SymbolLayer(id = "symbols") {
            iconImage(Expression.get("icon-url"))
            iconSize(0.2f)
          }
        }
      }
    }
  }
}

private const val TEST_DATA = """
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "properties": {
        "icon-url": "https://em-content.zobj.net/source/google/412/sparkles_2728.png"
      },
      "geometry": {
        "coordinates": [
          -45.63127044854389,
          27.557988242330993
        ],
        "type": "Point"
      },
      "id": 0
    },
    {
      "type": "Feature",
      "properties": {
        "icon-url": "https://em-content.zobj.net/source/google/412/sparkles_2728.png"
      },
      "geometry": {
        "coordinates": [
          -10.827954182253734,
          12.775372856374815
        ],
        "type": "Point"
      },
      "id": 1
    },
    {
      "type": "Feature",
      "properties": {
        "icon-url": "https://em-content.zobj.net/source/google/412/cooking_1f373.png"
      },
      "geometry": {
        "coordinates": [
          -38.621388676971975,
          -4.073737846092399
        ],
        "type": "Point"
      },
      "id": 2
    }
  ]
}
"""
