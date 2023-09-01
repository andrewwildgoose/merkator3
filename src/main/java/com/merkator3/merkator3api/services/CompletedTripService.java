package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.trip.planned.CompletedTrip;
import com.merkator3.merkator3api.models.trip.planned.CompletedTripResponse;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompletedTripService {
    CompletedTripResponse tripCompletionError(ObjectId tripId);

    CompletedTripResponse handleTripCompletion(ObjectId tripId, List<String> routeId, List<MultipartFile> file)
            throws IOException;

    List<String> getTripCompletedGpxStrings(CompletedTrip trip);
}
