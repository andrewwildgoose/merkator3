package com.merkator3.merkator3api.services.route;

import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Interface defining the Completed Route Services
 */

public interface CompletedRouteService {

    CompletedRoute getCompletedRoute(ObjectId id);

    String getRouteGpxAsJSON(ObjectId id) throws IOException;

    void setRouteStaticMapUrl(CompletedRoute route, String mapBoxKey);

    String getRouteStaticMapURL(CompletedRoute route, String mapBoxKey);
}
