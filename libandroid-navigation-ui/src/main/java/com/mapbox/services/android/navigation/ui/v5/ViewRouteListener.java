package com.mapbox.services.android.navigation.ui.v5;

import com.mapbox.services.android.navigation.v5.models.DirectionsRoute;
import org.maplibre.geojson.Point;

interface ViewRouteListener {

  void onRouteUpdate(DirectionsRoute directionsRoute);

  void onRouteRequestError(String errorMessage);

  void onDestinationSet(Point destination);
}
