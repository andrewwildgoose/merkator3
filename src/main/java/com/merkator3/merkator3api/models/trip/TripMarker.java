package com.merkator3.merkator3api.models.trip;

import org.bson.types.ObjectId;

import java.util.List;

public interface TripMarker {

    List<ObjectId> getTripRoutes();

    void setTripStaticMapUrl(String mapUrl);
}
