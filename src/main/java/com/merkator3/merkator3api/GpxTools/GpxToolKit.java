package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.GPX;

import java.io.IOException;
import java.nio.file.Path;

public class GpxToolKit {

    private GPX gpx;

    final GPX gpxBuilder = GPX.builder()
            .addTrack(track -> track
                    .addSegment(segment -> segment
                            .addPoint(p -> p.lat(48.20100).lon(16.31651).ele(283))
                            .addPoint(p -> p.lat(48.20112).lon(16.31639).ele(278))
                            .addPoint(p -> p.lat(48.20126).lon(16.31601).ele(274))))
            .build();


    public final void gpxWriter(GPX gpx, Path path) throws IOException {
        GPX.write(gpx, path);
    }




    // Reading GPX 1.0 file.



}
