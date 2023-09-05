package com.merkator3.merkator3api.models.trip;

import com.merkator3.merkator3api.models.route.planned.Route;
import org.bson.types.ObjectId;

import java.util.List;

public interface TripMarker {

    List<ObjectId> getTripRoutes();

    void setTripStaticMapUrl(String mapUrl);
}
