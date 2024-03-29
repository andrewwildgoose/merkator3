package com.merkator3.merkator3api.repositories;

import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository representing the Completed Routes collection on the database
 */

@RepositoryRestResource(collectionResourceRel = "completedRoutes", path = "completedRoutes")
public interface CompletedRouteRepository extends MongoRepository<CompletedRoute, String> {

    CompletedRoute findByRouteName(@Param("route") String route);

    CompletedRoute findById(@Param("id") ObjectId id);
}
