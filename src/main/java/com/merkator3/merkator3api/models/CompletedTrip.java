//package com.merkator3.merkator3api.models;
//
//import com.merkator3.merkator3api.MapTools.MapBuilder;
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import java.util.List;
//
///**
// * A CompletedTrip is a collection of Routes of which the user has undertaken at least one
// * which they want to analyse together.
// */
//@Document(collection = "completedTrips")
//public class CompletedTrip extends Trip {
//
//    @Id
//    private ObjectId id;
//    @Field("tripName") private String tripName;
//    @Field("hasParentTrip") private Boolean hasParentTrip;
//    @Field("parentTripName") private String parentTripName;
//    @Field("parentTripId") private ObjectId parentTripId;
//    @Field("tripDescription") private String tripDescription;
//    @Field("tripRoutes") private List<Route> tripRoutes;
//    @Field("tripStaticMapURL") private String tripStaticMapUrl;
//
//    public CompletedTrip(String tripName, Boolean hasParentTrip){
//        super(tripName);
//        this.hasParentTrip = hasParentTrip;
//    };
//
//    public void setParentTripName(String parentTripName) {
//        this.parentTripName = parentTripName;
//    }
//
//    public String getParentTripName() {
//        return parentTripName;
//    }
//
//    public void setParentTripId(ObjectId parentTripId) {
//        this.parentTripId = parentTripId;
//    }
//
//    public ObjectId getParentTripId() {
//        return parentTripId;
//    }
//}
