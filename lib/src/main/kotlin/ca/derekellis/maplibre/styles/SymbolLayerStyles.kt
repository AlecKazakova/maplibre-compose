package ca.derekellis.maplibre.styles

import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ca.derekellis.maplibre.layers.LayerDsl
import ca.derekellis.maplibre.layers.LayerScope
import org.maplibre.android.style.expressions.Expression
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.SymbolLayer

private typealias SymbolLayerScope = LayerScope<SymbolLayer>

public enum class SymbolPlacement(public val value: String) {
  Point("point"),
  Line("line"),
  LineCenter("line-center"),
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolPlacement(symbolPlacement: SymbolPlacement) {
  layer.withProperties(PropertyFactory.symbolPlacement(symbolPlacement.value))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolPlacement(expression: Expression) {
  layer.withProperties(PropertyFactory.symbolPlacement(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolSpacing(spacing: Float) {
  layer.withProperties(PropertyFactory.symbolSpacing(spacing))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolSpacing(expression: Expression) {
  layer.withProperties(PropertyFactory.symbolSpacing(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolAvoidEdges(avoid: Boolean) {
  layer.withProperties(PropertyFactory.symbolAvoidEdges(avoid))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolAvoidEdges(expression: Expression) {
  layer.withProperties(PropertyFactory.symbolAvoidEdges(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolSortKey(key: Float) {
  layer.withProperties(PropertyFactory.symbolSortKey(key))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolSortKey(expression: Expression) {
  layer.withProperties(PropertyFactory.symbolSortKey(expression))
}

public enum class SymbolZOrder(public val value: String) {
  Auto("auto"),
  ViewportY("viewport-y"),
  Source("source"),
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolZOrder(symbolZOrder: SymbolZOrder) {
  layer.withProperties(PropertyFactory.symbolZOrder(symbolZOrder.value))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.symbolZOrder(expression: Expression) {
  layer.withProperties(PropertyFactory.symbolZOrder(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconAllowOverlap(allowOverlap: Boolean) {
  layer.withProperties(PropertyFactory.iconAllowOverlap(allowOverlap))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconAllowOverlap(expression: Expression) {
  layer.withProperties(PropertyFactory.iconAllowOverlap(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconIgnorePlacement(ignorePlacement: Boolean) {
  layer.withProperties(PropertyFactory.iconIgnorePlacement(ignorePlacement))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconIgnorePlacement(expression: Expression) {
  layer.withProperties(PropertyFactory.iconIgnorePlacement(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconOptional(optional: Boolean) {
  layer.withProperties(PropertyFactory.iconOptional(optional))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconOptional(expression: Expression) {
  layer.withProperties(PropertyFactory.iconOptional(expression))
}

public enum class RotationAlignment(public val value: String) {
  Map("map"),
  Viewport("viewport"),
  Auto("auto"),
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconRotationAlignment(rotationAlignment: RotationAlignment) {
  layer.withProperties(PropertyFactory.iconRotationAlignment(rotationAlignment.value))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconRotationAlignment(expression: Expression) {
  layer.withProperties(PropertyFactory.iconRotationAlignment(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconSize(size: Float) {
  layer.withProperties(PropertyFactory.iconSize(size))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconSize(expression: Expression) {
  layer.withProperties(PropertyFactory.iconSize(expression))
}

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
public fun SymbolLayerScope.iconRotate(rotate: Float) {
  layer.withProperties(PropertyFactory.iconRotate(rotate))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconRotate(expression: Expression) {
  layer.withProperties(PropertyFactory.iconRotate(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconPadding(padding: Float) {
  layer.withProperties(PropertyFactory.iconPadding(padding))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconPadding(expression: Expression) {
  layer.withProperties(PropertyFactory.iconPadding(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconKeepUpright(keepUpright: Boolean) {
  layer.withProperties(PropertyFactory.iconKeepUpright(keepUpright))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconKeepUpright(expression: Expression) {
  layer.withProperties(PropertyFactory.iconKeepUpright(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconOffset(x: Float, y: Float) {
  layer.withProperties(PropertyFactory.iconOffset(arrayOf(x, y)))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconOffset(offset: Array<Float>) {
  layer.withProperties(PropertyFactory.iconOffset(offset))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconOffset(expression: Expression) {
  layer.withProperties(PropertyFactory.iconOffset(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textField(text: String) {
  layer.withProperties(PropertyFactory.textField(text))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textField(expression: Expression) {
  layer.withProperties(PropertyFactory.textField(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textColor(expression: Expression) {
  layer.withProperties(PropertyFactory.textColor(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textColor(@ColorInt color: Int) {
  layer.withProperties(PropertyFactory.textColor(color))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textColor(color: String) {
  layer.withProperties(PropertyFactory.textColor(color))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textColor(color: Color) {
  layer.withProperties(PropertyFactory.textColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textSize(expression: Expression) {
  layer.withProperties(PropertyFactory.textSize(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.textSize(value: Float) {
  layer.withProperties(PropertyFactory.textSize(value))
}

public enum class IconAnchor(public val value: String) {
  Center("center"),
  Left("left"),
  Right("right"),
  Top("top"),
  Bottom("bottom"),
  TopLeft("top-left"),
  TopRight("top-right"),
  BottomLeft("bottom-left"),
  BottomRight("bottom-right"),
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconAnchor(iconAnchor: IconAnchor) {
  layer.withProperties(PropertyFactory.iconAnchor(iconAnchor.value))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconAnchor(expression: Expression) {
  layer.withProperties(PropertyFactory.iconAnchor(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconOpacity(opacity: Float) {
  layer.withProperties(PropertyFactory.iconOpacity(opacity))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconOpacity(expression: Expression) {
  layer.withProperties(PropertyFactory.iconOpacity(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconColor(@ColorInt color: Int) {
  layer.withProperties(PropertyFactory.iconColor(color))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconColor(color: String) {
  layer.withProperties(PropertyFactory.iconColor(color))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconColor(color: Color) {
  layer.withProperties(PropertyFactory.iconColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconColor(expression: Expression) {
  layer.withProperties(PropertyFactory.iconColor(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloColor(@ColorInt color: Int) {
  layer.withProperties(PropertyFactory.iconHaloColor(color))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloColor(color: String) {
  layer.withProperties(PropertyFactory.iconHaloColor(color))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloColor(color: Color) {
  layer.withProperties(PropertyFactory.iconHaloColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloColor(expression: Expression) {
  layer.withProperties(PropertyFactory.iconHaloColor(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloWidth(haloWidth: Float) {
  layer.withProperties(PropertyFactory.iconHaloWidth(haloWidth))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloWidth(expression: Expression) {
  layer.withProperties(PropertyFactory.iconHaloWidth(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloBlur(haloBlur: Float) {
  layer.withProperties(PropertyFactory.iconHaloBlur(haloBlur))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconHaloBlur(expression: Expression) {
  layer.withProperties(PropertyFactory.iconHaloBlur(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconTranslate(x: Float, y: Float) {
  layer.withProperties(PropertyFactory.iconTranslate(arrayOf(x, y)))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconTranslate(offset: Array<Float>) {
  layer.withProperties(PropertyFactory.iconTranslate(offset))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconTranslate(expression: Expression) {
  layer.withProperties(PropertyFactory.iconTranslate(expression))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconTranslateAnchor(translateAnchor: TranslateAnchor) {
  layer.withProperties(PropertyFactory.iconTranslateAnchor(translateAnchor.value))
}

@Composable
@LayerDsl
public fun SymbolLayerScope.iconTranslateAnchor(expression: Expression) {
  layer.withProperties(PropertyFactory.iconTranslateAnchor(expression))
}
