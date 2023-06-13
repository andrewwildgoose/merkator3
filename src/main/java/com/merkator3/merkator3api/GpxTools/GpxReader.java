package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class GpxReader {

    public final List<WayPoint> gpxToPointList(String pathString) throws IOException {
        return GPX.read(Path.of(pathString)).tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .collect(Collectors.toList());
    }

}
