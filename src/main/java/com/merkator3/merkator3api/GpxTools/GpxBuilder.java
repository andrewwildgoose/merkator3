package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Class for building GPX objects from a given list of points
public class GpxBuilder {



    public final GPX gpxBuilder(List<WayPoint> pointList) {
        GPX gpx = GPX.builder()
                .addTrack(track -> track.addSegment(TrackSegment.Builder::build)).build();
        TrackSegment gpxSegment = gpx.getTracks().get(0).getSegments().get(0);
        for (WayPoint point : pointList) {
            gpx(point);
        }

        GPX.Builder gpxBuilder = GPX.builder();
        Track.Builder trackBuilder = gpxBuilder.addTrack();
        TrackSegment.Builder segmentBuilder = trackBuilder.addSegment();

        for (WayPoint wayPoint : pointsList) {
            segmentBuilder.addPoint(p ->
                    p.lat(wayPoint.getLatitude())
                            .lon(wayPoint.getLongitude())
                            .ele(wayPoint.getElevation())
            );
        }

        GPX gpx = gpxBuilder.build();
    }
}
