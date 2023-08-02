package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.Route;
import org.bson.types.ObjectId;
import io.jenetics.jpx.GPX;

import java.io.IOException;

public interface RouteService {

    ObjectId addRoute(ObjectId userID, String routeName, GPX file) throws IOException;

    Route getRoute(ObjectId id);
}
