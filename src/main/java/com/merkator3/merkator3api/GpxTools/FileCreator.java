package com.merkator3.merkator3api.GpxTools;
import java.io.File;
import java.io.IOException;
public class FileCreator {
    public static void createFile(String fileName) {
        File file = new File(fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }
}

