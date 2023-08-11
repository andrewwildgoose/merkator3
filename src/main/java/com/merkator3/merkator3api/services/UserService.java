package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {

    MerkatorUser createUser(String email, String password);

    MerkatorUser getUser(ObjectId userId);

    MerkatorUser findByEmail(String email);

    boolean deleteRoute(ObjectId userId, ObjectId routeId);
}
