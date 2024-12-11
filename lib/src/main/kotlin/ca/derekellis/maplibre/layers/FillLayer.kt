package ca.derekellis.maplibre.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import ca.derekellis.maplibre.compose.LayerNode
import ca.derekellis.maplibre.compose.MapNodeApplier
import ca.derekellis.maplibre.sources.SourceDsl
import ca.derekellis.maplibre.sources.SourceScope
import org.maplibre.android.style.layers.FillLayer

@Composable
@SourceDsl
public fun SourceScope.FillLayer(
  id: String,
  styles: @Composable LayerScope<FillLayer>.() -> Unit = {},
) {
  val layer = remember { FillLayer(id, sourceId) }
  val scope = remember {
    object : LayerScope<FillLayer>, SourceScope by this {
      override val layer: FillLayer get() = layer
    }
  }

  ComposeNode<LayerNode, MapNodeApplier>(
    factory = { LayerNode(id, layer) },
    update = {
      // TODO
    },
    content = { scope.styles() },
  )
}
