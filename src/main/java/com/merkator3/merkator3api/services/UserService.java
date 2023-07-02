package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.User;
import org.bson.types.ObjectId;

public interface UserService {

    User createUser(String userName);

    // READ
    User getUser(ObjectId userId);
}
