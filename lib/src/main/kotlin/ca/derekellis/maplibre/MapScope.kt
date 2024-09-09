package ca.derekellis.maplibre

import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style

@MapDsl
public interface MapScope {
  public val map: MapLibreMap

  public val style: Style
}
