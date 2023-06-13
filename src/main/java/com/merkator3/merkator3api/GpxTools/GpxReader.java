package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Point;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class GpxReader {

    public final List<Point> gpxToPointList(String pathString) throws IOException {
        return GPX.read(Path.of(pathString)).tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .collect(Collectors.toList());
    }

}
