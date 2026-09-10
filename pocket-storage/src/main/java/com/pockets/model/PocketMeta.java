package com.pockets.model;

public class PocketMeta {

    private String id;
    private String node;
    private long size;
    private String checksum;
    private String path;

    public PocketMeta() {}

    public PocketMeta(String id, String node, long size, String checksum, String path) {
        this.id = id;
        this.node = node;
        this.size = size;
        this.checksum = checksum;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getNode() {
        return node;
    }

    public long getSize() {
        return size;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getPath() {
        return path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
