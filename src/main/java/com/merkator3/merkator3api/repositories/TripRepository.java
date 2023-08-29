package com.merkator3.merkator3api.repositories;

import com.merkator3.merkator3api.models.PlannedTrip;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trips", path = "trips")
public interface TripRepository extends MongoRepository<PlannedTrip, String> {
    PlannedTrip findByTripName(@Param("trip") String trip);

    PlannedTrip findById(@Param("id") ObjectId id);
}