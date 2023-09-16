package com.merkator3.merkator3api.models.route.planned;

import org.bson.types.ObjectId;
import lombok.Builder;
import lombok.Data;

/**
 * Representation of a request to add a Route to a Trip on the database
 */

@Data
@Builder
public class AddRouteToTripRequest {

    private ObjectId tripId;
    private ObjectId routeId;

    public AddRouteToTripRequest(ObjectId tripId, ObjectId routeId) {
        this.tripId = tripId;
        this.routeId = routeId;
    }
    public ObjectId getTripId() {
        return tripId;
    }
    public ObjectId getRouteId() {
        return routeId;
    }
}
