package com.merkator3.merkator3api.repositories;

import com.merkator3.merkator3api.models.trip.completed.CompletedTrip;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository representing the Completed Trips collection on the database
 */
@RepositoryRestResource(collectionResourceRel = "completedTrips", path = "completedTrips")
public interface CompletedTripRepository extends MongoRepository<CompletedTrip, String> {
    CompletedTrip findByTripName(@Param("completedTrip") String completedTrip);

    CompletedTrip findById(@Param("id") ObjectId id);
}