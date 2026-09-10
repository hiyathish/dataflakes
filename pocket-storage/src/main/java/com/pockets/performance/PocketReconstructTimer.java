package com.pockets.performance;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class PocketReconstructTimer {

    public static void reconstructWithTiming(List<Path> pocketPaths, Path outputFile) throws IOException {

        Instant totalStart = Instant.now();
        Instant writeStart = Instant.now();

        try (BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(outputFile))) {

            byte[] buffer = new byte[1024 * 1024]; // 1MB buffer

            for (Path pocket : pocketPaths) {

                Instant readStart = Instant.now();

                try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(pocket))) {
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }

                Instant readEnd = Instant.now();
                long readMillis = Duration.between(readStart, readEnd).toMillis();

                System.out.println("Read time for pocket " + pocket.getFileName() + ": " + readMillis + " ms");
            }
        }

        Instant writeEnd = Instant.now();
        Instant totalEnd = Instant.now();

        long writeMillis = Duration.between(writeStart, writeEnd).toMillis();
        long totalMillis = Duration.between(totalStart, totalEnd).toMillis();

        System.out.println("--------------------------------------------------");
        System.out.println("Write time (writing final large file): " + writeMillis + " ms");
        System.out.println("Total reconstruct time (read + write): " + totalMillis + " ms");
        System.out.println("--------------------------------------------------");
    }
}
