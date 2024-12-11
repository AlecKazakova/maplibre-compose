package ca.derekellis.maplibre.styles

import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ca.derekellis.maplibre.layers.LayerDsl
import ca.derekellis.maplibre.layers.LayerScope
import org.maplibre.android.style.expressions.Expression
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory

private typealias LineLayerScope = LayerScope<LineLayer>

public enum class LineCap(public val value: String) {
  Butt("butt"),
  Round("round"),
  Square("square"),
}

@Composable
@LayerDsl
public fun LineLayerScope.lineCap(lineCap: LineCap) {
  layer.withProperties(PropertyFactory.lineCap(lineCap.value))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineCap(expression: Expression) {
  layer.withProperties(PropertyFactory.lineCap(expression))
}

public enum class LineJoin(public val value: String) {
  Bevel("bevel"),
  Round("round"),
  Miter("miter"),
}

@Composable
@LayerDsl
public fun LineLayerScope.lineJoin(lineJoin: LineJoin) {
  layer.withProperties(PropertyFactory.lineJoin(lineJoin.value))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineJoin(expression: Expression) {
  layer.withProperties(PropertyFactory.lineJoin(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineMiterLimit(limit: Float) {
  layer.withProperties(PropertyFactory.lineMiterLimit(limit))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineMiterLimit(expression: Expression) {
  layer.withProperties(PropertyFactory.lineMiterLimit(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineRoundLimit(limit: Float) {
  layer.withProperties(PropertyFactory.lineRoundLimit(limit))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineRoundLimit(expression: Expression) {
  layer.withProperties(PropertyFactory.lineRoundLimit(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineSortKey(key: Float) {
  layer.withProperties(PropertyFactory.lineSortKey(key))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineSortKey(expression: Expression) {
  layer.withProperties(PropertyFactory.lineSortKey(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineOpacity(opacity: Float) {
  layer.withProperties(PropertyFactory.lineOpacity(opacity))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineOpacity(expression: Expression) {
  layer.withProperties(PropertyFactory.lineOpacity(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineColor(@ColorInt color: Int) {
  layer.withProperties(PropertyFactory.lineColor(color))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineColor(color: String) {
  layer.withProperties(PropertyFactory.lineColor(color))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineColor(color: Color) {
  layer.withProperties(PropertyFactory.lineColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineColor(expression: Expression) {
  layer.withProperties(PropertyFactory.lineColor(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineTranslate(x: Float, y: Float) {
  layer.withProperties(PropertyFactory.lineTranslate(arrayOf(x, y)))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineTranslate(offset: Array<Float>) {
  layer.withProperties(PropertyFactory.lineTranslate(offset))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineTranslate(expression: Expression) {
  layer.withProperties(PropertyFactory.lineTranslate(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineTranslateAnchor(translateAnchor: TranslateAnchor) {
  layer.withProperties(PropertyFactory.lineTranslateAnchor(translateAnchor.value))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineTranslateAnchor(expression: Expression) {
  layer.withProperties(PropertyFactory.lineTranslateAnchor(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineWidth(width: Float) {
  layer.withProperties(PropertyFactory.lineWidth(width))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineWidth(expression: Expression) {
  layer.withProperties(PropertyFactory.lineWidth(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineGapWidth(width: Float) {
  layer.withProperties(PropertyFactory.lineGapWidth(width))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineGapWidth(expression: Expression) {
  layer.withProperties(PropertyFactory.lineGapWidth(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineOffset(offset: Float) {
  layer.withProperties(PropertyFactory.lineOffset(offset))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineOffset(expression: Expression) {
  layer.withProperties(PropertyFactory.lineOffset(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineBlur(blur: Float) {
  layer.withProperties(PropertyFactory.lineBlur(blur))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineBlur(expression: Expression) {
  layer.withProperties(PropertyFactory.lineBlur(expression))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineDasharray(array: Array<Float>) {
  layer.withProperties(PropertyFactory.lineDasharray(array))
}

@Composable
@LayerDsl
public fun LineLayerScope.lineDasharray(expression: Expression) {
  layer.withProperties(PropertyFactory.lineDasharray(expression))
}
