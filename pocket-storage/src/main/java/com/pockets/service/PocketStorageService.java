package com.pockets.service;

import com.pockets.config.PocketProperties;
import com.pockets.metadata.CatalogService;
import com.pockets.model.Catalog;
import com.pockets.model.PocketMeta;
import com.pockets.storage.StorageStrategy;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HexFormat;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PocketStorageService {

    private final StorageStrategy storageStrategy;
    private final CatalogService catalogService;
    private final PocketProperties properties;

    public PocketStorageService(StorageStrategy storageStrategy,
                                CatalogService catalogService,
                                PocketProperties properties) {
        this.storageStrategy = storageStrategy;
        this.catalogService = catalogService;
        this.properties = properties;
    }

    public void splitFile(String filePath) throws Exception {
        Catalog catalog = new Catalog();
        catalog.setFile(filePath);

        int nodeIndex = 0;
        int pocketNum = 0;

        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[properties.getSize()];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                byte[] pocketData = Arrays.copyOf(buffer, bytesRead);

                String pocketId = "pocket_" + pocketNum;
                String node = properties.getClusterNodes().get(nodeIndex);

                String path = storageStrategy.storePocket(pocketId, pocketData, node);

                PocketMeta meta = new PocketMeta(
                        pocketId,
                        node,
                        bytesRead,
                        md5(pocketData),
                        path
                );

                catalog.getPockets().add(meta);

                pocketNum++;
                nodeIndex = (nodeIndex + 1) % properties.getClusterNodes().size();
            }
        }

        catalogService.saveCatalog(catalog);
    }

    public void reconstruct(String outputFile) throws Exception {
        Catalog catalog = catalogService.loadCatalog();

        if (Paths.get(outputFile).getParent() != null) {
            Files.createDirectories(Paths.get(outputFile).getParent());
        }

        try (OutputStream out = new FileOutputStream(outputFile)) {
            for (PocketMeta pocket : catalog.getPockets()) {
                byte[] data = Files.readAllBytes(Paths.get(pocket.getPath()));
                out.write(data);
            }
        }
    }

    private String md5(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(data);
        return HexFormat.of().formatHex(digest);
    }
}
