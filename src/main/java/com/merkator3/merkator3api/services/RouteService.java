package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.route.planned.RouteResponse;
import org.bson.types.ObjectId;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RouteService {

    ObjectId addRoute(ObjectId userID, String routeName, MultipartFile file) throws IOException;
    Route getRoute(ObjectId id) throws IOException;

    Map<String, String> getRouteDetails(ObjectId id) throws IOException, JSONException;

    String getRouteGpxAsJSON(ObjectId id) throws IOException, JSONException;

    List<Route> getUserRoutes(ObjectId id);

    RouteResponse getRouteResponse(ObjectId routeId) throws IOException;

    List<RouteResponse> getRouteResponsesForUser(ObjectId id);

    boolean routeBelongsToUser(ObjectId userId, ObjectId routeId);

    boolean deleteRoute(ObjectId routeId);
}
