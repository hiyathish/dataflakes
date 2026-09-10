package com.pockets.storage;

public interface StorageStrategy {
    String storePocket(String pocketId, byte[] data, String node) throws Exception;
}
