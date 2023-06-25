package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

// Class for building GPX objects from a given list of points
public class GpxBuilder {



    public final GPX gpxBuilder(List<WayPoint> pointList) {
        GPX.Builder gpx = GPX.builder();

        gpx.addTrack(track -> track.addSegment(segmentBuilder -> {
            for (WayPoint point : pointList) {
                segmentBuilder.addPoint(pointBuilder -> pointBuilder
                                .lat(point.getLatitude())
                                .lon(point.getLongitude())
                                .ele(point.getElevation().get()));
                }
            }));

            return gpx.build();
        }

    public final GPX gpxBuilder(Path path) throws IOException {
        return GPX.read(path);
    }

}

