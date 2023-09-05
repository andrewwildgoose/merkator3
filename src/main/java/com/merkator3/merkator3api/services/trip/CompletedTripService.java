package com.merkator3.merkator3api.services.trip;

import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import com.merkator3.merkator3api.models.trip.completed.CompletedTrip;
import com.merkator3.merkator3api.models.trip.completed.CompletedTripResponse;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompletedTripService {
    CompletedTripResponse tripCompletionError(ObjectId tripId);


    // Method to handle creating of a new completed trip and return it to the controller.
    String handleTripCompletion(ObjectId userID, ObjectId tripId, List<String> routeId, List<MultipartFile> file)
            throws IOException;

    boolean tripBelongsToUser(ObjectId userID, ObjectId tripID);

    CompletedTripResponse getCompletedTrip(ObjectId tripId);

    List<CompletedRoute> createCompletedRoutes(List<String> routeIds, List<MultipartFile> gpxFiles)
            throws IOException;

    List<CompletedRoute> getCompletedRoutes(CompletedTrip trip);

    List<String> getTripCompletedGpxStrings(CompletedTrip trip);

    List<String> getCompletedRouteNames(CompletedTrip trip);

    void setTripStaticMapUrl(String mapBoxKey, CompletedTrip trip);

    String getTripStaticMapUrl(CompletedTrip completedTrip);
}
