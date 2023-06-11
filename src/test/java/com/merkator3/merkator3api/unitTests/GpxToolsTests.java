package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.GpxTools.GpxToolKit;
import io.jenetics.jpx.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Path;

public class GpxToolsTests {

    @Test
    void testReadGPX() throws IOException {
        GPX.read(Path.of("src/test/TestFiles/GPX/London to Brighton Return.gpx")).tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .forEach(System.out::println);
    }
    @Test
    void testWriteGPXToFile() throws IOException {
        GpxToolKit gpxToolKit = new GpxToolKit();
        GPX gpx = GPX.builder()
                .addTrack(track -> track
                        .addSegment(segment -> segment
                                .addPoint(p -> p.lat(48.20100).lon(16.31651).ele(283))
                                .addPoint(p -> p.lat(48.20112).lon(16.31639).ele(278))
                                .addPoint(p -> p.lat(48.20126).lon(16.31601).ele(274))))
                .build();
        gpxToolKit.gpxWriter(gpx, Path.of("src/test/TestFiles/GPX/test.gpx"));
    }
}
