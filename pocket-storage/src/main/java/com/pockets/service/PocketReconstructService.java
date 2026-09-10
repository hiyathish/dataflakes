package com.pockets.service;

import com.pockets.metadata.CatalogService;
import com.pockets.model.Catalog;
import com.pockets.model.PocketMeta;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PocketReconstructService {

    private final CatalogService catalogService;

    public PocketReconstructService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * Reconstructs the original file from pockets using streaming IO.
     * Structure is preserved because pockets are written in catalog order.
     */
    public void reconstruct(String outputFile) throws Exception {
        Catalog catalog = catalogService.loadCatalog();

        if (Paths.get(outputFile).getParent() != null) {
            Files.createDirectories(Paths.get(outputFile).getParent());
        }

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            byte[] buffer = new byte[1024 * 1024]; // 1 MB buffer

            for (PocketMeta pocket : catalog.getPockets()) {
                try (InputStream in = new BufferedInputStream(
                        Files.newInputStream(Paths.get(pocket.getPath())))) {

                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }
}
