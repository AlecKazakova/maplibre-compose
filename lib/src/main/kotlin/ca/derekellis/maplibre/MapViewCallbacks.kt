package ca.derekellis.maplibre

import org.maplibre.android.maps.MapView

/**
 * Exposes callbacks on [MapView] in a view-agnostic way.
 *
 * TODO: Add other callbacks
 */
public interface MapViewCallbacks {
  public fun addOnStyleImageMissingListener(listener: MapView.OnStyleImageMissingListener)
  public fun removeOnStyleImageMissingListener(listener: MapView.OnStyleImageMissingListener)
}
