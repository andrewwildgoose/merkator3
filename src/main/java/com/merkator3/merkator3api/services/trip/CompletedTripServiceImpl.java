package com.merkator3.merkator3api.services.trip;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import com.merkator3.merkator3api.StatTools.CompletedTripCalculator;
import com.merkator3.merkator3api.StatTools.TripCalculator;
import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.completed.CompletedTrip;
import com.merkator3.merkator3api.models.trip.completed.CompletedTripResponse;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import com.merkator3.merkator3api.repositories.CompletedRouteRepository;
import com.merkator3.merkator3api.repositories.CompletedTripRepository;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.TripRepository;
import com.merkator3.merkator3api.services.route.RouteService;
import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private TripService tripService;
    @Autowired
    private RouteService routeService;
    @Value("${merkator.api.mapBoxKey}")
    private String mapBoxKey;

    private final TripCalculator tripCalc = new TripCalculator();
    private final CompletedTripCalculator compTripCalc = new CompletedTripCalculator();

    public CompletedTripServiceImpl(CompletedTripRepository completedTripRepository, TripRepository tripRepository,
                                    RouteRepository routeRepository, CompletedRouteRepository completedRouteRepository,
                                    TripService tripService, RouteService routeService) {
        this.completedTripRepository = completedTripRepository;
        this.completedRouteRepository = completedRouteRepository;
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
        this.tripService = tripService;
        this.routeService = routeService;
    }

    @Override
    public CompletedTripResponse tripCompletionError(ObjectId tripId) {
        return new CompletedTripResponse("Error completing this trip");
    }

    // Method to handle creating of a new completed trip and return it to the controller.
    @Override
    public String handleTripCompletion(ObjectId tripId, List<String> routeId, List<MultipartFile> file)
            throws IOException {
        Trip parentTrip = tripRepository.findById(tripId);

        // Create completed trip and set its attributes
        CompletedTrip completedTrip = new CompletedTrip(parentTrip.getTripName(), true);
        completedTrip.setTripDescription("Completed trip based on " + parentTrip.getTripName());
        completedTrip.setParentTripId(tripId);
        completedTrip.setParentTripName(parentTrip.getTripName());
        List<CompletedRoute> completedRoutes = createCompletedRoutes(routeId, file);
        completedTrip.setCompletedRoutes(completedRoutes);

        // Save the newly completed trip and routes to the db.
        completedRouteRepository.saveAll(completedRoutes);
        completedTripRepository.save(completedTrip);

        // Calculate values that are used multiple times in the response
        Double completedDistance = compTripCalc.totalCompletedDistance(completedTrip);
        Double elapsedTime = compTripCalc.calculateTripElapsedTime(completedTrip);
        Double movingTime = compTripCalc.calculateTripMovingTime(completedTrip);

        // Build and return the completed trip response.
        return completedTrip.getId().toString();
    }

    @Override
    public CompletedTripResponse getCompletedTrip(ObjectId tripId) {
        CompletedTrip completedTrip = completedTripRepository.findById(tripId);
        Trip parentTrip = tripRepository.findById(completedTrip.getParentTripId());

        // Calculate values that are used multiple times in the response
        Double completedDistance = compTripCalc.totalCompletedDistance(completedTrip);
        Double elapsedTime = compTripCalc.calculateTripElapsedTime(completedTrip);
        Double movingTime = compTripCalc.calculateTripMovingTime(completedTrip);

        // Build and return the completed trip response.
        return new CompletedTripResponse(
                completedTrip.getId(),
                completedTrip.getId().toString(),
                completedTrip.getTripName(),
                completedTrip.getTripDescription(),
                tripCalc.totalDistance(completedTrip),
                tripCalc.totalElevationGain(completedTrip),
                tripCalc.totalElevationLoss(completedTrip),
                completedDistance,
                compTripCalc.totalCompletedElevationGain(completedTrip),
                compTripCalc.totalCompletedElevationLoss(completedTrip),
                elapsedTime,
                movingTime,
                compTripCalc.calculateAvgSpeed(completedDistance, elapsedTime),
                compTripCalc.calculateAvgSpeed(completedDistance, elapsedTime),
                completedTrip.getTripRouteNames(),
                completedTrip.getTripCompletedRouteNames(),
                tripService.getTripGpxStrings(completedTrip),
                getTripCompletedGpxStrings(completedTrip),
                parentTrip.getTripStaticMapUrl(mapBoxKey),
                completedTrip.getTripRoutes().size(),
                completedTrip.getCompletedRoutes().size()
        );
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

    @Override
    public List<String> getTripCompletedGpxStrings(CompletedTrip trip) {
        return trip.getCompletedRoutes().stream()
                .map(route -> {
                    try {
                        return routeService.getRouteGpxAsJSON(route.getId());
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

}
