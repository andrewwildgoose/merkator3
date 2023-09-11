package com.merkator3.merkator3api.GpxTools;

import com.merkator3.merkator3api.models.Coordinate;
import io.jenetics.jpx.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GpxReader {

    // method used to obtain list of WayPoints from a file path.
    public final List<WayPoint> gpxToPointList(String pathString) throws IOException {
        return GPX.read(Path.of(pathString)).tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .collect(Collectors.toList());
    }

    public static List<WayPoint> gpxToPointList(GPX gpx) {
        return gpx.tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .collect(Collectors.toList());
    }

    // method used to obtain a list of WayPoints directly from a GPX object.
    public static List<Coordinate> extractCoordinatesFromGpx(GPX gpx) {
        List<WayPoint> wayPoints = gpxToPointList(gpx);

        return wayPoints.stream()
                .map(wayPoint -> new Coordinate(wayPoint.getLatitude().doubleValue(), wayPoint.getLongitude().doubleValue()))
                .collect(Collectors.toList());
    }
}
