package com.merkator3.merkator3api.models;

import org.bson.types.ObjectId;
import java.util.List;

/**
 * A Trip is a collection of Routes which the user wants to analyse together.
 */

public interface Trip {

    ObjectId getId();

    String getTripName();

    void setTripName(String tripName);

    String getTripDescription();

    void setTripDescription(String tripDescription);

    List<Route> getTripRoutes();

    void setTripRoutes(List<Route> tripRoutes);

    void setTripStaticMapUrl(String mapBoxKey);

    String getTripStaticMapUrl(String mapBoxKey);
}
