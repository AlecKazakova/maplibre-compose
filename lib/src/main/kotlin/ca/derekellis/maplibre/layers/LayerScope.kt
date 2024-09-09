package ca.derekellis.maplibre.layers

import ca.derekellis.maplibre.MapScope
import org.maplibre.android.style.layers.Layer

@LayerDsl
public interface LayerScope<T : Layer> : MapScope {
  public val layer: T
}
