package ca.derekellis.maplibre.samples

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ca.derekellis.maplibre.DemoStyle
import ca.derekellis.maplibre.MapLibreMap
import ca.derekellis.maplibre.Navigator
import ca.derekellis.maplibre.Screen
import ca.derekellis.maplibre.layers.CircleLayer
import ca.derekellis.maplibre.layers.FillLayer
import ca.derekellis.maplibre.rememberMapState
import ca.derekellis.maplibre.sources.GeoJsonSource
import ca.derekellis.maplibre.styles.circleColor
import ca.derekellis.maplibre.styles.circleOpacity
import ca.derekellis.maplibre.styles.circleRadius
import ca.derekellis.maplibre.styles.fillColor
import ca.derekellis.maplibre.styles.fillOpacity
import ca.derekellis.maplibre.styles.fillOutlineColor
import kotlinx.coroutines.delay
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLngBounds
import org.maplibre.android.geometry.LatLngBounds.Companion
import org.maplibre.geojson.Polygon
import org.maplibre.turf.TurfMeasurement
import java.net.URI
import kotlin.math.round

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StylesSample(navigator: Navigator) {
  Scaffold(topBar = {
    SampleAppBar(title = "Styles Sample", onNavigate = { navigator.goTo(Screen.Home) })
  }) { innerPadding ->
    val bounds = remember { TurfMeasurement.bbox(PORTLAND_BOUNDS_GEOMETRY) }

    val mapState = rememberMapState(padding = innerPadding)
    var showSettings by remember { mutableStateOf(false) }
    var radiusSetting by remember { mutableFloatStateOf(5f) }
    var opacitySetting by remember { mutableFloatStateOf(1f) }
    var colorSetting by remember { mutableStateOf(colorForHue(0f)) }

    if (showSettings) {
      BackHandler {
        showSettings = false
      }
    }

    Box(
      modifier = Modifier.consumeWindowInsets(innerPadding),
    ) {
      MapLibreMap(
        modifier = Modifier.fillMaxSize(),
        style = DemoStyle.Default.url,
        state = mapState,
        contentPadding = innerPadding,
      ) {
        GeoJsonSource(
          id = "sample",
          uri = URI.create("https://raw.githubusercontent.com/georgique/world-geojson/develop/countries/canada.json"),
        ) {
          CircleLayer(id = "circles") {
            circleRadius(radius = radiusSetting)
            circleOpacity(opacity = opacitySetting)
            circleColor(color = colorSetting)
          }
        }

        GeoJsonSource(
          id = "portland",
          json = PORTLAND_POLYGON,
        ) {
          FillLayer(id = "portland_fill") {
            fillColor(Color.Magenta)
            fillOpacity(0.2f)
            fillOutlineColor(Color.Magenta)
          }
        }
      }
      Column(
        modifier = Modifier
          .fillMaxSize()
          .consumeWindowInsets(PaddingValues(16.dp))
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.End,
      ) {
        AnimatedVisibility(visible = showSettings) {
          Surface(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentHeight(),
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(16.dp),
          ) {
            Column(
              modifier = Modifier.padding(vertical = 16.dp),
              verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
              SliderControl(
                label = "Circle Radius",
                value = radiusSetting,
                range = 1f..10f,
                onChange = { radiusSetting = round(it) },
              )
              SliderControl(
                label = "Circle Opacity",
                value = opacitySetting,
                range = 0f..1f,
                onChange = { opacitySetting = it },
              )
              SliderColorControl(
                label = "CircleColor",
                value = colorSetting,
                onChange = { colorSetting = it },
              )
            }
          }
        }
        ExtendedFloatingActionButton(
          onClick = { showSettings = !showSettings },
          icon = { Icon(Icons.Default.List, contentDescription = "Open Settings") },
          text = { Text(text = "Settings") },
        )
      }
    }

    LaunchedEffect(bounds) {
      delay(1)
      mapState.easeTo(CameraUpdateFactory.newLatLngBounds(LatLngBounds.from(bounds[3], bounds[2], bounds[1], bounds[0]), 0))
    }
  }
}

@Composable
private fun SliderControl(
  label: String,
  value: Float,
  range: ClosedFloatingPointRange<Float>,
  onChange: (Float) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
  ) {
    Text(text = "$label: $value")
    Slider(value = value, valueRange = range, onValueChange = onChange)
  }
}

@Composable
private fun SliderColorControl(
  label: String,
  value: Color,
  onChange: (Color) -> Unit,
) {
  var hue by remember { mutableFloatStateOf(0f) }
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
  ) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(text = "$label:")
      Box(
        modifier = Modifier
          .size(24.dp)
          .background(value),
      )
    }
    Slider(value = hue, onValueChange = {
      hue = it
      onChange(colorForHue(it * 360))
    })
  }
}

private fun colorForHue(hue: Float) = Color.hsv(hue, 0.5f, 0.8f)

const val PORTLAND_POLYGON = """
  {
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "properties": {},
      "geometry": {
        "coordinates": [
          [
            [
              -122.67751732080666,
              45.556570078537476
            ],
            [
              -122.67846976800969,
              45.54772368780317
            ],
            [
              -122.67491764780198,
              45.54391954196217
            ],
            [
              -122.67442658084583,
              45.556504162340616
            ],
            [
              -122.67751732080666,
              45.556570078537476
            ]
          ]
        ],
        "type": "Polygon"
      }
    }
  ]
}
"""

const val PORTLAND_BOUNDS = """
  {
        "coordinates": [
          [
            [
              -122.71033342864496,
              45.556883078880986
            ],
            [
              -122.71033342864496,
              45.489529017727676
            ],
            [
              -122.63293821436281,
              45.489529017727676
            ],
            [
              -122.63293821436281,
              45.556883078880986
            ],
            [
              -122.71033342864496,
              45.556883078880986
            ]
          ]
        ],
        "type": "Polygon"
      }
"""

private val PORTLAND_BOUNDS_GEOMETRY = Polygon.fromJson(PORTLAND_BOUNDS)