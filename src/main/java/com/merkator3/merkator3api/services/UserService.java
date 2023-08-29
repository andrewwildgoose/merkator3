package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
import org.bson.types.ObjectId;

public interface UserService {

    MerkatorUser createUser(String email, String password);

    MerkatorUser getUser(ObjectId userId);

    MerkatorUser findByEmail(String email);

    boolean deleteRoute(ObjectId userId, ObjectId routeId);

    boolean deleteTrip(ObjectId userId, ObjectId tripId);
}
