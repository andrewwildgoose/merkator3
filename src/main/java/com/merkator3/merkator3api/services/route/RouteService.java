package com.merkator3.merkator3api.services.route;

import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.route.planned.RouteResponse;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RouteService {

    ObjectId addRoute(ObjectId userID, String routeName, MultipartFile file) throws IOException;
    Route getRoute(ObjectId id);

    Map<String, String> getRouteDetails(ObjectId id);

    String getRouteGpxAsJSON(ObjectId id) throws IOException;

    List<Route> getUserRoutes(ObjectId id);

    RouteResponse getRouteResponse(ObjectId routeId) throws IOException;

    List<RouteResponse> getRouteResponsesForUser(ObjectId id);

    boolean routeBelongsToUser(ObjectId userId, ObjectId routeId);

    boolean deleteRoute(MerkatorUser user, ObjectId routeId);

    void setRouteStaticMapUrl(Route route, String mapBoxKey);

    String getRouteStaticMapURL(Route route, String mapBoxKey);
}
