package com.merkator3.merkator3api.services.trip;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.StatTools.CompletedTripCalculator;
import com.merkator3.merkator3api.StatTools.TripCalculator;
import com.merkator3.merkator3api.models.route.RouteMarker;
import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.completed.CompletedTrip;
import com.merkator3.merkator3api.models.trip.completed.CompletedTripResponse;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.repositories.*;
import com.merkator3.merkator3api.services.route.CompletedRouteService;
import com.merkator3.merkator3api.services.route.RouteService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@DependsOn({"tripRepository", "routeRepository", "completedTripRepository", "completedRouteRepository"})
public class CompletedTripServiceImpl implements CompletedTripService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompletedTripRepository completedTripRepository;
    @Autowired
    private CompletedRouteRepository completedRouteRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private TripService tripService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private CompletedRouteService completedRouteService;
    @Value("${merkator.api.mapBoxKey}")
    private String mapBoxKey;
    private final TripCalculator tripCalc = new TripCalculator();
    private final CompletedTripCalculator compTripCalc = new CompletedTripCalculator();
    public CompletedTripServiceImpl(UserRepository userRepository, CompletedTripRepository completedTripRepository,
                                    TripRepository tripRepository, RouteRepository routeRepository,
                                    CompletedRouteRepository completedRouteRepository, TripService tripService,
                                    RouteService routeService, CompletedRouteService completedRouteService) {
        this.userRepository = userRepository;
        this.completedTripRepository = completedTripRepository;
        this.completedRouteRepository = completedRouteRepository;
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
        this.tripService = tripService;
        this.routeService = routeService;
        this.completedRouteService = completedRouteService;
    }

    @Override
    public CompletedTripResponse tripCompletionError(ObjectId tripId) {
        return new CompletedTripResponse("Error completing this trip");
    }

    // Method to handle creating of a new completed trip and return it to the controller.
    @Override
    public String handleTripCompletion(ObjectId userID, ObjectId tripId, List<String> routeId, List<MultipartFile> file)
            throws IOException {
        Trip parentTrip = tripRepository.findById(tripId);
        MerkatorUser user = userRepository.findById(userID);

        // Create completed trip and set its attributes
        CompletedTrip completedTrip = new CompletedTrip(parentTrip.getTripName(), true);
        completedTrip.setTripDescription("Completed trip based on " + parentTrip.getTripName());
        completedTrip.setParentTripId(tripId);
        completedTrip.setParentTripName(parentTrip.getTripName());
        completedTrip.setTripRoutes(tripService.getTripRoutes(parentTrip));
        List<CompletedRoute> completedRoutes = createCompletedRoutes(routeId, file);
        completedTrip.setCompletedRoutes(completedRoutes);
        setTripStaticMapUrl(mapBoxKey, completedTrip);


        // Save the newly completed trip and routes to the db.
        completedRouteRepository.saveAll(completedRoutes);
        completedTripRepository.save(completedTrip);

        // Update the user
        user.addCompletedTrip(completedTrip.getId());
        userRepository.save(user);

        // Return the completed trip's ID.
        return completedTrip.getId().toString();
    }

    @Override
    public boolean tripBelongsToUser(ObjectId userID, ObjectId tripID) {
        MerkatorUser user = userRepository.findById(userID);
        return user.getUserCompletedTrips().contains(tripID);
    }

    @Override
    public CompletedTripResponse getCompletedTrip(ObjectId tripId) {
        CompletedTrip completedTrip = completedTripRepository.findById(tripId);
        List<CompletedRoute> completedRoutes = getCompletedRoutes(completedTrip);
        Trip parentTrip = tripRepository.findById(completedTrip.getParentTripId());
        List<Route> plannedRoutes = tripService.getTripRoutes(parentTrip);


        // Calculate values that are used multiple times in the response
        Double completedDistance = compTripCalc.totalCompletedDistance(completedRoutes);
        Double elapsedTime = compTripCalc.calculateTripElapsedTime(completedRoutes);
        Double movingTime = compTripCalc.calculateTripMovingTime(completedRoutes);

        // Build and return the completed trip response.
        return new CompletedTripResponse(
                completedTrip.getId(),
                completedTrip.getId().toString(),
                completedTrip.getTripName(),
                completedTrip.getTripDescription(),
                tripCalc.totalDistance(plannedRoutes),
                tripCalc.totalElevationGain(plannedRoutes),
                tripCalc.totalElevationLoss(plannedRoutes),
                completedDistance,
                compTripCalc.totalCompletedElevationGain(completedRoutes),
                compTripCalc.totalCompletedElevationLoss(completedRoutes),
                elapsedTime,
                movingTime,
                compTripCalc.calculateAvgSpeed(completedDistance, elapsedTime),
                compTripCalc.calculateAvgSpeed(completedDistance, elapsedTime),
                tripService.getTripRouteNames(completedTrip),
                getCompletedRouteNames(completedTrip),
                tripService.getTripGpxStrings(completedTrip),
                tripService.getTripRouteColours(parentTrip),
                getTripCompletedGpxStrings(completedTrip),
                getTripStaticMapUrl(completedTrip),
                completedTrip.getTripRoutes().size(),
                completedTrip.getCompletedRoutes().size()
        );
    }




    // Method for taking a list of routes to be completed & creating them.
    @Override
    public List<CompletedRoute> createCompletedRoutes(List<String> routeIds, List<MultipartFile> gpxFiles)
            throws IOException {
        List<CompletedRoute> completedRoutes = new ArrayList<>();
        CompletedRoute completedRoute;

        for (int i = 0; i < routeIds.size(); i++) {
            String routeId = routeIds.get(i);
            MultipartFile gpxFile = gpxFiles.get(i);

            Optional<Route> optionalParentRoute = routeRepository.findById(routeId);

            if (optionalParentRoute.isPresent()) {
                Route parentRoute = optionalParentRoute.get();

                completedRoute = new CompletedRoute(parentRoute.getRouteName(), true);

                // Process and set the completed route's parent attributes
                completedRoute.setParentRouteName(parentRoute.getRouteName());
                completedRoute.setParentRouteId(parentRoute.getId());
                completedRoute.setRouteDescription("Completed route based on " + parentRoute.getRouteName());

            } else {
                completedRoute = new CompletedRoute(gpxFile.getOriginalFilename(), false);
            }
            // Create local temp file to use to populate the GpxString.
            Path tempFile = Files.createTempFile("temp", ".gpx");
            gpxFile.transferTo(tempFile.toFile());
            completedRoute.setRouteGpxString(tempFile);
            completedRouteRepository.save(completedRoute);

            // Add the completed route to the list
            completedRoutes.add(completedRoute);
        }
        return completedRoutes;
    }

    @Override
    public List<CompletedRoute> getCompletedRoutes(CompletedTrip trip) {
        return trip.getCompletedRoutes().stream()
                .map(route -> completedRouteRepository.findById(route))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTripCompletedGpxStrings(CompletedTrip trip) {
        return trip.getCompletedRoutes().stream()
                .map(route -> completedRouteRepository.findById(route))
                .map(route -> {
                    try {
                        return completedRouteService.getRouteGpxAsJSON(route.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public void addCompletedRoute(CompletedRoute completedRoute, CompletedTrip trip) {

        ObjectId parentRouteId = completedRoute.getParentRouteId();

        // Check if a completed route with the same parentRouteId already exists
        Optional<CompletedRoute> existingCompletedRouteOptional = trip.getCompletedRoutes().stream()
                .map(route -> {
                    try {
                        return completedRouteService.getCompletedRoute(route);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(route -> route.getParentRouteId().equals(parentRouteId))
                .findFirst();

        if (existingCompletedRouteOptional.isPresent()) {
            // Replace the existing completed route with the new one
            CompletedRoute existingCompletedRoute = existingCompletedRouteOptional.get();
            int existingRouteIndex = trip.getCompletedRoutes().indexOf(existingCompletedRoute.getId());
            trip.addCompletedRoute(existingRouteIndex, completedRoute.getId());
        } else {
            // No existing completed route with the same parentRouteId, add the new one
            trip.addCompletedRoute(completedRoute.getId());
        }
    }

    @Override
    public List<String> getCompletedRouteNames(CompletedTrip trip) {
        return trip.getCompletedRoutes().stream()
                .map(route -> completedRouteRepository.findById(route))
                .map(CompletedRoute::getRouteName)
                .collect(Collectors.toList());
    }

    @Override
    public void setTripStaticMapUrl(String mapBoxKey, CompletedTrip trip) {
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        List<RouteMarker> combinedRoutes = new ArrayList<>();
        combinedRoutes.addAll(tripService.getTripRoutes(trip));
        combinedRoutes.addAll(getCompletedRoutes(trip));
        String mapUrl = mapBuilder.generateStaticMapImageUrl(combinedRoutes);
        trip.setTripStaticMapUrl(mapUrl);
    }

    @Override
    public String getTripStaticMapUrl(CompletedTrip completedTrip) {
        if (completedTrip.getTripStaticMapUrl() == null) {
            setTripStaticMapUrl(mapBoxKey, completedTrip);
        }
        return completedTrip.getTripStaticMapUrl();
    }
}
