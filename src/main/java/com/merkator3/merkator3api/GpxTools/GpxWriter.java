package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.GPX;

import java.io.IOException;
import java.nio.file.Path;

public class GpxWriter {

    // TODO: 13/06/2023 consider combining GPX tools to one class & making a singleton. Or keeping separate but still a singleton.

    /* function to take a GPX object and write it to a
    file at the specified path location.*/
    public final void gpxWriter(GPX gpx, Path path) throws IOException {
        GPX.write(gpx, path);
    }
}
