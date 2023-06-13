package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;

import java.io.IOException;
import java.nio.file.Path;

// Class for building GPX objects from a given list of points
public class GpxBuilder {

    public final GPX gpxBuilder(){
        return GPX.builder()
                .addTrack(track -> track
                        .addSegment(segment -> segment
                                .addPoint(p -> p.lat(48.20100).lon(16.31651).ele(283))
                                .addPoint(p -> p.lat(48.20112).lon(16.31639).ele(278))
                                .addPoint(p -> p.lat(48.20126).lon(16.31601).ele(274))))
                .build();
    }

}
