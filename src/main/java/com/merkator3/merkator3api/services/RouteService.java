package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.Route;
import org.bson.types.ObjectId;
import io.jenetics.jpx.GPX;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RouteService {

    ObjectId addRoute(ObjectId userID, String routeName, GPX file) throws IOException;

    Route getRoute(ObjectId id) throws IOException;

    Map<String, String> getRouteDetails(ObjectId id) throws IOException, JSONException;

    String getRouteGpxAsJSON(ObjectId id) throws IOException, JSONException;

    List<Route> getUserRoutes(ObjectId id);
}
