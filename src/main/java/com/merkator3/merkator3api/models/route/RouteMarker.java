package com.merkator3.merkator3api.models.route;

import io.jenetics.jpx.GPX;

import java.io.IOException;
import java.util.List;

public interface RouteMarker {
    GPX getRouteGpx() throws IOException;

    List<Integer> getMapLineColor();

    void setMapLineColor(int red, int green, int blue);

    String getRouteGpxString();
}
