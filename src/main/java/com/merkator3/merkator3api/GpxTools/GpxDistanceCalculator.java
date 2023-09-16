package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.*;
import io.jenetics.jpx.Length;
import io.jenetics.jpx.geom.Geoid;
import org.apache.commons.math3.util.Precision;

import java.util.stream.Stream;

/**
 * Class for calculating the route distance of a given GPX object
 */

public class GpxDistanceCalculator {

    public Length calculateDistance(GPX gpx){
        return gpx.tracks()
                .flatMap(Track::segments)
                .findFirst()
                .map(TrackSegment::points).orElse(Stream.empty())
                .collect(Geoid.WGS84.toPathLength());
    }

    public Double lengthToKm(Length distance) {
        return Precision.round(distance.doubleValue()/1000, 2);
    }
}
