package org.maplibre.navigation.android.navigation.ui.v5.instruction;

import org.maplibre.navigation.core.models.BannerComponents;

class TextVerifier implements NodeVerifier {
  @Override
  public boolean isNodeType(BannerComponents bannerComponents) {
    return bannerComponents.getText() != null && !bannerComponents.getText().isEmpty();
  }
}
