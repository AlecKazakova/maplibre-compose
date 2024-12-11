package ca.derekellis.maplibre.styles

import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ca.derekellis.maplibre.layers.LayerDsl
import ca.derekellis.maplibre.layers.LayerScope
import org.maplibre.android.style.expressions.Expression
import org.maplibre.android.style.layers.FillLayer
import org.maplibre.android.style.layers.PropertyFactory

private typealias FillLayerScope = LayerScope<FillLayer>

@Composable
@LayerDsl
public fun FillLayerScope.fillAntialias(antialias: Boolean) {
  layer.withProperties(PropertyFactory.fillAntialias(antialias))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillAntialias(expression: Expression) {
  layer.withProperties(PropertyFactory.fillAntialias(expression))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillOpacity(opacity: Float) {
  layer.withProperties(PropertyFactory.fillOpacity(opacity))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillOpacity(expression: Expression) {
  layer.withProperties(PropertyFactory.fillAntialias(expression))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillColor(@ColorInt color: Int) {
  layer.withProperties(PropertyFactory.fillColor(color))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillColor(color: String) {
  layer.withProperties(PropertyFactory.fillColor(color))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillColor(color: Color) {
  layer.withProperties(PropertyFactory.fillColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillColor(expression: Expression) {
  layer.withProperties(PropertyFactory.fillColor(expression))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillOutlineColor(@ColorInt color: Int) {
  layer.withProperties(PropertyFactory.fillOutlineColor(color))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillOutlineColor(color: String) {
  layer.withProperties(PropertyFactory.fillOutlineColor(color))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillOutlineColor(color: Color) {
  layer.withProperties(PropertyFactory.fillOutlineColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillOutlineColor(expression: Expression) {
  layer.withProperties(PropertyFactory.fillOutlineColor(expression))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillTranslate(x: Float, y: Float) {
  layer.withProperties(PropertyFactory.fillTranslate(arrayOf(x, y)))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillTranslate(offset: Array<Float>) {
  layer.withProperties(PropertyFactory.fillTranslate(offset))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillTranslate(expression: Expression) {
  layer.withProperties(PropertyFactory.fillTranslate(expression))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillTranslateAnchor(translateAnchor: TranslateAnchor) {
  layer.withProperties(PropertyFactory.fillTranslateAnchor(translateAnchor.value))
}

@Composable
@LayerDsl
public fun FillLayerScope.fillTranslateAnchor(expression: Expression) {
  layer.withProperties(PropertyFactory.fillTranslateAnchor(expression))
}
