package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Metadata;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        route.setRouteGpx(gpx);
        route.setRouteDescription(String.valueOf(gpx.getMetadata().flatMap(Metadata::getDescription)));
        route = routeRepository.save(route);

        // add the route to the user's routes
        MerkatorUser user = userRepository.findById(userID);
        user.addRoute(route.getId());
        userRepository.save(user);
        return route.getId();
    }
    //TODO: flagged for deletion
    // without file
//    @Override
//    public ObjectId addRoute(ObjectId userID, String routeName) {
//        Route route = new Route(routeName);
//        route = routeRepository.save(route);
//
//        // add the route to the user's routes
//        MerkatorUser user = userRepository.findById(userID);
//        user.addRoute(route.getId());
//        userRepository.save(user);
//        return route.getId();
//    }

    @Override
    public Route getRoute(ObjectId id) {
        return routeRepository.findById(id);
    }
}
