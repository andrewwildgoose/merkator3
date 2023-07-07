package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.Route;
import org.bson.types.ObjectId;
import io.jenetics.jpx.GPX;

public interface RouteService {

    ObjectId addRoute(ObjectId userID, String routeName, GPX file);

    Route getRoute(ObjectId id);
}
