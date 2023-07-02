package com.merkator3.merkator3api.models;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {

    @Id private ObjectId id;
    @Field("userName") private String userName;
    @Field("userRoutes") private List<ObjectId> userRoutes;
    @Field("userTrips") private List<ObjectId> userTrips;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        // need to check for duplicate userName
        this.userName = userName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void addRoute(ObjectId routeId) {
        if (userRoutes == null){
            userRoutes = new ArrayList<>();
        }
        userRoutes.add(routeId);
    }

    public List<ObjectId> getUserRoutes() {
        return userRoutes;
    }

    public void addTrip(ObjectId tripId) {
        if (userTrips == null) {
            userTrips = new ArrayList<>();
        }
        userTrips.add(tripId);
    }

    public List<ObjectId> getUserTrips() {
        return userTrips;
    }
}