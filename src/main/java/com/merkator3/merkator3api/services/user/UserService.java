package com.merkator3.merkator3api.services.user;

import com.merkator3.merkator3api.models.user.MerkatorUser;
import org.bson.types.ObjectId;

public interface UserService {

    MerkatorUser createUser(String email, String password);

    void deleteUser(ObjectId userId);

    MerkatorUser getUser(ObjectId userId);

    MerkatorUser findByEmail(String email);

    boolean deleteRoute(ObjectId userId, ObjectId routeId);

    boolean deleteTrip(ObjectId userId, ObjectId tripId);
}
