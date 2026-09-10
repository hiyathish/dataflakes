package com.pockets.storage;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LocalStorageStrategy implements StorageStrategy {

    @Override
    public String storePocket(String pocketId, byte[] data, String node) throws Exception {
        Files.createDirectories(Paths.get(node));
        String path = node + "/" + pocketId + ".pocket";
        Files.write(Paths.get(path), data);
        return path;
    }
}
