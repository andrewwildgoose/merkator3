package com.merkator3.merkator3api.repositories;

import com.merkator3.merkator3api.models.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trips", path = "trips")
public interface TripRepository extends MongoRepository<Trip, String> {
    Trip findByTripName(@Param("trip") String trip);
}