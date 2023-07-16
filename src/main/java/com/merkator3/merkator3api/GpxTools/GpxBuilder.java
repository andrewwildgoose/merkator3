package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
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

    public static GPX convertMultipartFileToGPX(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("temp", file.getOriginalFilename());
        Files.write(tempFile, file.getBytes(), StandardOpenOption.CREATE);
        return GPX.read(tempFile);
    }

    public static Metadata populateGPXMetadataTime(Metadata metadata) {
        Metadata.Builder metadataBuilder = Metadata.builder();

        metadata.getBounds().ifPresent(metadataBuilder::bounds);
        metadata.getExtensions().ifPresent(metadataBuilder::extensions);
        metadata.getName().ifPresent(metadataBuilder::name);
        metadata.getDescription().ifPresent(metadataBuilder::desc);
        metadata.getAuthor().ifPresent(metadataBuilder::author);

        List<Link> links = metadata.getLinks();
        if (!links.isEmpty()) {
            links.forEach(metadataBuilder::addLink);
        }

        metadata = metadataBuilder
                .time(Instant.now())
                .build();

        return metadata;
    }

}

