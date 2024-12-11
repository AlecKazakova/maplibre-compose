package ca.derekellis.maplibre.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import ca.derekellis.maplibre.compose.LayerNode
import ca.derekellis.maplibre.compose.MapNodeApplier
import ca.derekellis.maplibre.sources.SourceDsl
import ca.derekellis.maplibre.sources.SourceScope
import org.maplibre.android.style.layers.SymbolLayer

@Composable
@SourceDsl
public fun SourceScope.SymbolLayer(
  id: String,
  styles: @Composable LayerScope<SymbolLayer>.() -> Unit = {},
) {
  val layer = remember { SymbolLayer(id, sourceId) }
  val scope = remember {
    object : LayerScope<SymbolLayer>, SourceScope by this {
      override val layer: SymbolLayer get() = layer
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
