package ca.derekellis.maplibre.styles

import androidx.compose.runtime.Composable
import ca.derekellis.maplibre.layers.LayerDsl
import ca.derekellis.maplibre.layers.LayerScope
import org.maplibre.android.style.expressions.Expression
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.SymbolLayer

private typealias SymbolLayerScope = LayerScope<SymbolLayer>

@Composable
@LayerDsl
public fun SymbolLayerScope.iconImage(image: String) {
  layer.withProperties(PropertyFactory.iconImage(image))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconImage(expression: Expression) {
  layer.withProperties(PropertyFactory.iconImage(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconSize(size: Float) {
  layer.withProperties(PropertyFactory.iconSize(size))
}
