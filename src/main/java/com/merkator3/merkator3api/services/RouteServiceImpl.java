package com.merkator3.merkator3api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Metadata;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@DependsOn({"routeRepository", "userRepository"})
public class RouteServiceImpl implements RouteService{

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private final UserRepository  userRepository;

    public RouteServiceImpl(RouteRepository routeRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    // with file
    @Override
    public ObjectId addRoute(ObjectId userID, String routeName, GPX gpx) throws IOException {
        // create and save the route to the repo
        Route route = new Route(routeName);
        route.setRouteGpxString(gpx);
        route.setRouteDescription(String.valueOf(gpx.getMetadata().flatMap(Metadata::getDescription)));
        route = routeRepository.save(route);

        // add the route to the user's routes
        MerkatorUser user = userRepository.findById(userID);
        user.addRoute(route.getId());
        userRepository.save(user);
        return route.getId();
    }

    @Override
    public Route getRoute(ObjectId id) throws IOException {
        return routeRepository.findById(id);
    }

    @Override
    public Map<String, String> getRouteDetails(ObjectId id) throws IOException, JSONException {
        Route route = routeRepository.findById(id);
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("route_name", route.getRouteName());
        jsonMap.put("route_description", route.getRouteDescription());
        return jsonMap;
    }

    @Override
    public String getRouteGpxAsJSON(ObjectId id) throws IOException, JSONException {
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

    public static String convertXmlToJson(String xml) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode node = xmlMapper.readTree(xml.getBytes());
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(node);
    }
}
