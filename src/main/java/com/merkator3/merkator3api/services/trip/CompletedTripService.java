package com.merkator3.merkator3api.services.trip;

import com.merkator3.merkator3api.models.trip.completed.CompletedTrip;
import com.merkator3.merkator3api.models.trip.completed.CompletedTripResponse;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompletedTripService {
    CompletedTripResponse tripCompletionError(ObjectId tripId);

    String handleTripCompletion(ObjectId tripId, List<String> routeId, List<MultipartFile> file)
            throws IOException;

    CompletedTripResponse getCompletedTrip(ObjectId tripId);

    List<String> getTripCompletedGpxStrings(CompletedTrip trip);
}
