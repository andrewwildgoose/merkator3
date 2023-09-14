package com.merkator3.merkator3api.services.route;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.GpxTools.GpxElevationCalculator;
import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.route.planned.RouteResponse;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Metadata;
import io.jsonwebtoken.lang.Assert;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@DependsOn({"routeRepository", "userRepository"})
public class RouteServiceImpl implements RouteService{


    private final RouteRepository routeRepository;

    private final UserRepository  userRepository;
    private final GpxDistanceCalculator gpxDistCalc = new GpxDistanceCalculator();
    private final GpxElevationCalculator gpxElevCalc = new GpxElevationCalculator();

    @Value("${merkator.api.mapBoxKey}")
    private String mapBoxKey;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, UserRepository userRepository) {
        Assert.notNull(userRepository, "userRepository must not be null");
        Assert.notNull(routeRepository, "routeRepository must not be null");
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    // with file
    @Override
    public ObjectId addRoute(ObjectId userID, String routeName, MultipartFile file) throws IOException {
        MerkatorUser user = userRepository.findById(userID);

        GPX fileGPX = GpxBuilder.convertMultipartFileToGPX(file);

        Route route = new Route(routeName);
        route.setRouteGpxString(fileGPX);
        route.setRouteDescription(String.valueOf(fileGPX.getMetadata().flatMap(Metadata::getDescription)));
        setRouteStaticMapUrl(route, mapBoxKey);
        route = routeRepository.save(route);

        user.addRoute(route.getId());
        userRepository.save(user);

        return route.getId();
    }

    @Override
    public Route getRoute(ObjectId id){
        return routeRepository.findById(id);
    }

    @Override
    public Map<String, String> getRouteDetails(ObjectId id) {
        Route route = routeRepository.findById(id);
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("route_name", route.getRouteName());
        jsonMap.put("route_description", route.getRouteDescription());
        return jsonMap;
    }

    @Override
    public String getRouteGpxAsJSON(ObjectId id) throws IOException {
        Route route = routeRepository.findById(id);
        String routeGpxString = route.getRouteGpxString();
        return convertXmlToJson(routeGpxString);
    }

    @Override
    public List<Route> getUserRoutes(ObjectId userId) {
        MerkatorUser user = userRepository.findById(userId);
        List<ObjectId> userRouteIds = user.getUserRoutes();
        List<String> routeIdsString = userRouteIds.stream()
                .map(ObjectId::toString)
                .collect(Collectors.toList());
        return routeRepository.findAllById(routeIdsString);
    }

    @Override
    @Transactional()
    public RouteResponse getRouteResponse(ObjectId routeId) throws IOException {
        Route route = routeRepository.findById(routeId);
        GPX routeGpx = route.getRouteGpx();

        try {
            return new RouteResponse(
                    route.getId(),
                    route.getId().toString(),
                    route.getRouteName(),
                    route.getRouteDescription(),
                    gpxDistCalc.lengthToKm(
                            gpxDistCalc.calculateDistance(
                                    routeGpx)),
                    gpxElevCalc.calculateElevationGain(routeGpx),
                    gpxElevCalc.calculateElevationLoss(routeGpx),
                    getRouteGpxAsJSON(route.getId()),
                    getRouteStaticMapURL(route, mapBoxKey),
                    route.getMapLineColor()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional()
    public List<RouteResponse> getRouteResponsesForUser(ObjectId id) {
        List<Route> routes = getUserRoutes(id);

        return routes.stream()
                .map(route -> {
                    try {
                        return getRouteResponse(route.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }


    @Override
    public boolean routeBelongsToUser(ObjectId userId, ObjectId routeId) {
        Route route = routeRepository.findById(routeId);
        MerkatorUser user = userRepository.findById(userId);
        return user.getUserRoutes().contains(routeId);
    }

    @Override
    public boolean deleteRoute(MerkatorUser user, ObjectId routeId) {
        try {
            // Remove route from user's route list
            user.getUserRoutes().remove(routeId);
            userRepository.save(user);
            // Remove route from route repository
            routeRepository.deleteById(String.valueOf(routeId));
            return true; // Route deleted successfully
        } catch (Exception e) {
            return false;
        }
    }

    public static String convertXmlToJson(String xml) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode node = xmlMapper.readTree(xml.getBytes());
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(node);
    }

    @Override
    public void setRouteStaticMapUrl(Route route, String mapBoxKey) {
        List<Route> singleRouteList = List.of(route);
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        route.setRouteStaticMapUrl(mapBuilder.generateStaticMapImageUrl(singleRouteList));
    }


    @Override
    public String getRouteStaticMapURL(Route route, String mapBoxKey) {
        if (route.getRouteStaticMapURL() == null) {
            setRouteStaticMapUrl(route, mapBoxKey);
        }
        return route.getRouteStaticMapURL();
    }
}
