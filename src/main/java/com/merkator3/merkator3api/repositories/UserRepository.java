package com.merkator3.merkator3api.repositories;

import com.merkator3.merkator3api.models.MerkatorUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends MongoRepository<MerkatorUser, String> {

    MerkatorUser findByEmail(@Param("email") String email);

    MerkatorUser findById(@Param("id") ObjectId id);

}
