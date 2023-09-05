package com.merkator3.merkator3api.services.route;

import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import com.merkator3.merkator3api.models.route.planned.Route;
import org.bson.types.ObjectId;

import java.io.IOException;

public interface CompletedRouteService {

    CompletedRoute getCompletedRoute(ObjectId id) throws IOException;

    String getRouteGpxAsJSON(ObjectId id) throws IOException;

    void setRouteStaticMapUrl(CompletedRoute route, String mapBoxKey);

    String getRouteStaticMapURL(CompletedRoute route, String mapBoxKey);
}
