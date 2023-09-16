package com.merkator3.merkator3api.models.trip;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Interface linking planned and completed trips to allow generification of relevant methods
 */

public interface TripMarker {

    List<ObjectId> getTripRoutes();

    void setTripStaticMapUrl(String mapUrl);
}
