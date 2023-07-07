package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserService {

    MerkatorUser createUser(String username);

    // READ
    MerkatorUser getUser(ObjectId userId);
}
