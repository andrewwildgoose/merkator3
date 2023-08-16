package com.merkator3.merkator3api.models;

import org.bson.types.ObjectId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRouteToTripRequest {

    private ObjectId tripID;
    private ObjectId routeID;

    public AddRouteToTripRequest(ObjectId tripID, ObjectId routeID) {
        this.tripID = tripID;
        this.routeID = routeID;
    }
    public ObjectId getTripId() {
        return tripID;
    }
    public ObjectId getRouteId() {
        return routeID;
    }
}
