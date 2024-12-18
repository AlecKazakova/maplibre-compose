package ca.derekellis.maplibre

import org.maplibre.android.maps.MapLibreMap

@MapDsl
public interface MapScope {
  public val map: MapLibreMap

  public val mapViewCallbacks: MapViewCallbacks
}
