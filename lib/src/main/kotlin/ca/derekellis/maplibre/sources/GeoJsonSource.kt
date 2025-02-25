package ca.derekellis.maplibre.sources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import ca.derekellis.maplibre.MapDsl
import ca.derekellis.maplibre.MapScope
import ca.derekellis.maplibre.compose.MapNodeApplier
import ca.derekellis.maplibre.compose.SourceNode
import org.maplibre.geojson.FeatureCollection
import java.net.URI
import org.maplibre.android.style.sources.GeoJsonSource as SdkGeoJsonSource

@Composable
@MapDsl
public fun MapScope.GeoJsonSource(
  id: String,
  uri: URI,
  layers: @Composable SourceScope.() -> Unit,
) {
  val scope = remember {
    object : SourceScope, MapScope by this {
      override val sourceId: String get() = id
    }
  }

  ComposeNode<SourceNode, MapNodeApplier>(
    factory = { SourceNode(id, SdkGeoJsonSource(id, uri)) },
    update = {
      // TODO: Update ID
      set(uri) {
        map.getStyle { it.getSourceAs<SdkGeoJsonSource>(id)?.setUri(uri) }
      }
    },
    content = { scope.layers() },
  )
}

@Composable
@MapDsl
public fun MapScope.GeoJsonSource(
  id: String,
  geojson: String,
  layers: @Composable SourceScope.() -> Unit,
) {
  val scope = remember {
    object : SourceScope, MapScope by this {
      override val sourceId: String get() = id
    }
  }

  ComposeNode<SourceNode, MapNodeApplier>(
    factory = { SourceNode(id, SdkGeoJsonSource(id, geojson)) },
    update = {
      // TODO: Update ID
      set(geojson) {
        map.getStyle { it.getSourceAs<SdkGeoJsonSource>(id)?.setGeoJson(geojson) }
      }
    },
    content = { scope.layers() },
  )
}

@Composable
@MapDsl
public fun MapScope.GeoJsonSource(
  id: String,
  featureCollection: FeatureCollection,
  layers: @Composable SourceScope.() -> Unit,
) {
  val scope = remember {
    object : SourceScope, MapScope by this {
      override val sourceId: String get() = id
    }
  }

  ComposeNode<SourceNode, MapNodeApplier>(
    factory = { SourceNode(id, SdkGeoJsonSource(id, featureCollection)) },
    update = {
      // TODO: Update ID
      set(featureCollection) {
        map.getStyle { it.getSourceAs<SdkGeoJsonSource>(id)?.setGeoJson(featureCollection) }
      }
    },
    content = { scope.layers() },
  )
}