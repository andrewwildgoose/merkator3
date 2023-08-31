//package com.merkator3.merkator3api.models;
//
//import com.merkator3.merkator3api.MapTools.MapBuilder;
//import io.jenetics.jpx.GPX;
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.TypeAlias;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.List;
//
//@Document(collection = "routes")
//@TypeAlias("completedRoute")
//public class CompletedRoute extends Route {
//
//    @Id
//    private ObjectId id;
//    @Field("routeName") private String routeName;
//    @Field("hasParentRoute") private Boolean hasParentRoute;
//    @Field("parentRouteName") private String parentRouteName;
//    @Field("parentRouteId") private ObjectId parentRouteId;
//    @Field("routeDescription") private String routeDescription;
//    @Field("routeGPXString") private String routeGpxString;
//    @Field("mapLineColor") private List<Integer> mapLineColor;
//    @Field("routeStaticMapURL") private String routeStaticMapUrl;
//
//    public CompletedRoute(String routeName, Boolean hasParentRoute) {
//        this.routeName = routeName;
//        this.hasParentRoute = hasParentRoute;
//    }
//
//    public void setParentRouteName(String parentRouteName) {
//        this.parentRouteName = parentRouteName;
//    }
//
//    public String getParentRouteName() {
//        return parentRouteName;
//    }
//
//    public void setParentRouteId(ObjectId parentRouteId) {
//        this.parentRouteId = parentRouteId;
//    }
//
//    public ObjectId getParentRouteId() {
//        return parentRouteId;
//    }
//
//}
