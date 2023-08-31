package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import com.merkator3.merkator3api.models.*;
import com.merkator3.merkator3api.repositories.CompletedRouteRepository;
import com.merkator3.merkator3api.repositories.CompletedTripRepository;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.TripRepository;
import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@DependsOn({"tripRepository", "routeRepository", "completedTripRepository", "completedRouteRepository"})
public class CompletedTripServiceImpl implements CompletedTripService {

    @Autowired
    private CompletedTripRepository completedTripRepository;
    @Autowired
    private CompletedRouteRepository completedRouteRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private RouteRepository routeRepository;

    public CompletedTripServiceImpl(CompletedTripRepository completedTripRepository, TripRepository tripRepository,
                                    RouteRepository routeRepository, CompletedRouteRepository completedRouteRepository) {
        this.completedTripRepository = completedTripRepository;
        this.completedRouteRepository = completedRouteRepository;
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public CompletedTripResponse tripCompletionError(ObjectId tripId) {
        return new CompletedTripResponse("Error completing this trip");
    }

    // Method to handle creating of a new completed trip and return it to the controller.
    @Override
    public CompletedTripResponse handleTripCompletion(ObjectId tripId, List<String> routeId, List<MultipartFile> file)
            throws IOException {
        Trip parentTrip = tripRepository.findById(tripId);

        // Create completed trip and set its attributes
        CompletedTrip completedTrip = new CompletedTrip(parentTrip.getTripName(), true);
        completedTrip.setTripDescription("Completed trip based on " + parentTrip.getTripName());
        completedTrip.setParentTripId(tripId);
        completedTrip.setParentTripName(parentTrip.getTripName());
        List<CompletedRoute> completedRoutes = createCompletedRoutes(routeId, file);
        completedRouteRepository.saveAll(completedRoutes);
        completedTrip.setCompletedRoutes(completedRoutes);

        return null;
    }

    // Method for taking a list of routes to be completed & creating them.
    public List<CompletedRoute> createCompletedRoutes(List<String> routeIds, List<MultipartFile> gpxFiles)
            throws IOException {
        List<CompletedRoute> completedRoutes = new ArrayList<>();

        for (int i = 0; i < routeIds.size(); i++) {
            String routeId = routeIds.get(i);
            MultipartFile gpxFile = gpxFiles.get(i);

            Optional<Route> optionalParentRoute = routeRepository.findById(routeId);

            if (optionalParentRoute.isPresent()) {
                Route parentRoute = optionalParentRoute.get();

                CompletedRoute completedRoute = new CompletedRoute(parentRoute.getRouteName(), true);

                // Process and set the completed route's attributes
                completedRoute.setParentRouteName(parentRoute.getRouteName());
                completedRoute.setParentRouteId(parentRoute.getId());
                completedRoute.setRouteDescription("Completed route based on " + parentRoute.getRouteName());
                GPX gpxData = GpxBuilder.convertMultipartFileToGPX(gpxFile);
                completedRoute.setRouteGpxString(gpxData);

                // Add the completed route to the list
                completedRoutes.add(completedRoute);
            }
        }
        return completedRoutes;
    }
}
