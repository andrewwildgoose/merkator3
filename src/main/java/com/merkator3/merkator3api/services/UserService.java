package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
import org.bson.types.ObjectId;

public interface UserService {

    MerkatorUser createUser(String username);

    MerkatorUser getUser(ObjectId userId);
}
