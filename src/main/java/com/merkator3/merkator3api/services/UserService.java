package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {

    MerkatorUser createUser(String username);

    MerkatorUser getUser(ObjectId userId);

    List<Route> getUserRoutes(ObjectId userId);
}
