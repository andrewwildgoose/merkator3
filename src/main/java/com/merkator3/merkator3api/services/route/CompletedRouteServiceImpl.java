package com.merkator3.merkator3api.services.route;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import com.merkator3.merkator3api.repositories.CompletedRouteRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.merkator3.merkator3api.services.route.RouteServiceImpl.convertXmlToJson;

@Service
public class CompletedRouteServiceImpl implements CompletedRouteService {

    @Autowired
    private CompletedRouteRepository completedRouteRepository;
    @Override
    public CompletedRoute getCompletedRoute(ObjectId id) throws IOException {
        return completedRouteRepository.findById(id);
    }
    @Override
    public String getRouteGpxAsJSON(ObjectId id) throws IOException {
        CompletedRoute route = completedRouteRepository.findById(id);
        String routeGpxString = route.getRouteGpxString();
        return convertXmlToJson(routeGpxString);
    }

    @Override
    public void setRouteStaticMapUrl(CompletedRoute route, String mapBoxKey) {
        List<CompletedRoute> singleRouteList = List.of(route);
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        route.setRouteStaticMapUrl(mapBuilder.generateStaticMapImageUrl(singleRouteList));
    }

    @Override
    public String getRouteStaticMapURL(CompletedRoute route, String mapBoxKey) {
        if (route.getRouteStaticMapURL() == null) {
            setRouteStaticMapUrl(route, mapBoxKey);
        }
        return route.getRouteStaticMapURL();
    }
}
