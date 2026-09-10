package com.pockets.model;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private String file;
    private List<PocketMeta> pockets = new ArrayList<>();

    public Catalog() {}

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<PocketMeta> getPockets() {
        return pockets;
    }

    public void setPockets(List<PocketMeta> pockets) {
        this.pockets = pockets;
    }
}
