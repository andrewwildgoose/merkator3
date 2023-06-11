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


}
