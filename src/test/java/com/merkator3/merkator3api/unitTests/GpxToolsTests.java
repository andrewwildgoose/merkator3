package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import com.merkator3.merkator3api.GpxTools.GpxReader;
import com.merkator3.merkator3api.GpxTools.GpxWriter;
import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import io.jenetics.jpx.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GpxToolsTests {

    public GPX gpx;
    public GPX lbl;

    @BeforeEach
    void createTestFile(){
        gpx = GPX.builder()
                .addTrack(track -> track
                        .addSegment(segment -> segment
                                .addPoint(p -> p.lat(48.20100).lon(16.31651).ele(283))
                                .addPoint(p -> p.lat(48.20112).lon(16.31639).ele(278))
                                .addPoint(p -> p.lat(48.20126).lon(16.31601).ele(274))))
                .build();
    }

    @BeforeEach
    void creatLBLGPX() throws IOException {
        Path path = Path.of("src/test/TestFiles/GPX/London to Brighton Return.gpx");
        lbl = GPX.read(path);
    }

    @AfterEach
    void deleteTestFile() {
        Path path = Path.of("src/test/TestFiles/GPX/test.gpx");
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.out.println("Failed to delete the file: " + e.getMessage());
        }
    }



    @Test
    void testWriteGPXToFile() throws IOException {
        Path path = Path.of("src/test/TestFiles/GPX/test.gpx");
        GpxWriter gpxWriter = new GpxWriter();
        gpxWriter.gpxWriter(gpx, path);
        Assertions.assertTrue(Files.exists(path), "GPX file has not been created");
    }

    // Test the gpxToPointList function generates a list with the expected number of points.
    @Test
    void testReadGPX() throws IOException {
        testWriteGPXToFile();
        int expectedSize = 3;
        GpxReader gpxReader = new GpxReader();
        List<WayPoint> gpxPoints = gpxReader.gpxToPointList("src/test/TestFiles/GPX/test.gpx");
        Assertions.assertEquals(expectedSize, gpxPoints.size());
    }

    // Test for the GpxBuidler class to ensure it adds Waypoints directly into a segment in a GPX object.
    @Test
    void testBuildWaypoints() throws IOException {
        testWriteGPXToFile();
        GpxReader gpxReader = new GpxReader();
        List<WayPoint> gpxPoints = gpxReader.gpxToPointList("src/test/TestFiles/GPX/test.gpx");

        GpxBuilder gpxBuilder = new GpxBuilder();
        GPX builderTest = gpxBuilder.gpxBuilder(gpxPoints);

        List<WayPoint> gpxFilePoints = gpx.getTracks().get(0)
                .getSegments().get(0)
                .getPoints();

        List<WayPoint> builderTestPoints = builderTest.getTracks().get(0)
                .getSegments().get(0)
                .getPoints();

        Assertions.assertEquals(gpxFilePoints, builderTestPoints);
    }

    @Test
    void testDistanceCalulator(){
        GpxDistanceCalculator gpxDistCalc = new GpxDistanceCalculator();
        Length distance = gpxDistCalc.calculateDistance(lbl);
        Assertions.assertEquals("201487.60385260044 m", distance.toString());
    }

    @Test
    void testLengthToKm() {
        GpxDistanceCalculator gpxDistCalc = new GpxDistanceCalculator();
        Length distance = gpxDistCalc.calculateDistance(lbl);
        Double distToKM = gpxDistCalc.lengthToKm(distance);
        Assertions.assertEquals(201.49, distToKM);
    }
}
