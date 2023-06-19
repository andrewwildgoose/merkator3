package com.merkator3.merkator3api.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "trips")
public class Trip {

    @Id
    private Long id;
    private String tripName;
    private String tripDescription;
    private List<Route> tripRoutes;

    public Trip(String tripName){
        this.tripName = tripName;
    };

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public List<Route> getTripRoutes() {
        return tripRoutes;
    }

    public void setTripRoutes(List<Route> tripRoutes) {
        this.tripRoutes = tripRoutes;
    }


}
