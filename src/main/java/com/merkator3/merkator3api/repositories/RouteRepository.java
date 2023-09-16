package com.merkator3.merkator3api.repositories;

import com.merkator3.merkator3api.models.route.planned.Route;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository representing the planned Routes collection on the database
 */

@RepositoryRestResource(collectionResourceRel = "routes", path = "routes")
public interface RouteRepository extends MongoRepository<Route, String>{
    Route findByRouteName(@Param("route") String route);

    Route findById(@Param("id") ObjectId id);

}

