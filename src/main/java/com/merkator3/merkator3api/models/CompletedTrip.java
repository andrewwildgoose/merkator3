package com.merkator3.merkator3api.models;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * A CompletedTrip is a collection of Routes of which the user has undertaken at least one
 * which they want to analyse together.
 */
public class CompletedTrip implements Trip {

    @Id
    private ObjectId id;
    @Field("tripName") private String tripName;
    @Field("hasParentTrip") private Boolean hasParentTrip;
    @Field("parentTripName") private String parentTripName;
    @Field("parentTripId") private ObjectId parentTripId;
    @Field("tripDescription") private String tripDescription;
    @Field("tripRoutes") private List<Route> tripRoutes;
    @Field("tripStaticMapURL") private String tripStaticMapUrl;

    public CompletedTrip(String tripName, Boolean hasParentTrip){
        this.tripName = tripName;
        this.hasParentTrip = hasParentTrip;
    };
    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public String getTripName() {
        return tripName;
    }

    public void setParentTripName(String parentTripName) {
        this.parentTripName = parentTripName;
    }

    public String getParentTripName() {
        return parentTripName;
    }

    public void setParentTripId(ObjectId parentTripId) {
        this.parentTripId = parentTripId;
    }

    public ObjectId getParentTripId() {
        return parentTripId;
    }

    @Override
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    @Override
    public String getTripDescription() {
        return tripDescription;
    }

    @Override
    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    @Override
    public List<Route> getTripRoutes() {
        return tripRoutes;
    }

    @Override
    public void setTripRoutes(List<Route> tripRoutes) {
        this.tripRoutes = tripRoutes;
    }

    @Override
    public void setTripStaticMapUrl(String mapBoxKey) {
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        this.tripStaticMapUrl = mapBuilder.generateStaticMapImageUrl(this.tripRoutes);
    }

    @Override
    public String getTripStaticMapUrl(String mapBoxKey) {
        if (this.tripStaticMapUrl == null) {
            this.setTripStaticMapUrl(mapBoxKey);
        }
        return this.tripStaticMapUrl;
    }
}
