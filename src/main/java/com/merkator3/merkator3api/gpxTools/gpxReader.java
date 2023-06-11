package com.merkator3.merkator3api.gpxTools;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.XMLProvider;

public class gpxReader {



    final GPX gpx = GPX.builder()
            .addTrack(track -> track
                    .addSegment(segment -> segment
                            .addPoint(p -> p.lat(48.20100).lon(16.31651).ele(283))
                            .addPoint(p -> p.lat(48.20112).lon(16.31639).ele(278))
                            .addPoint(p -> p.lat(48.20126).lon(16.31601).ele(274))))
            .build();





}
