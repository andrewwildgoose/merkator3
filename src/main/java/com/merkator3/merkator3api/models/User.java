package com.merkator3.merkator3api.models;

import org.apache.logging.log4j.ThreadContext;
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
        this.userRoutes = new ArrayList<>();
        this.userTrips = new ArrayList<>();
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
        userRoutes.add(routeId);
    }

    public List<ObjectId> getUserRoutes() {
        return userRoutes;
    }

    public void addTrip(ObjectId tripId) {
        userTrips.add(tripId);
    }

    public List<ObjectId> getUserTrips() {
        return userTrips;
    }
}