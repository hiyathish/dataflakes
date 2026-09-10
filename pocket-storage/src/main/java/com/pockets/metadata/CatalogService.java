package com.pockets.metadata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pockets.model.Catalog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class CatalogService {

    @Value("${metadata.catalogPath}")
    private String catalogPath;

    private final ObjectMapper mapper = new ObjectMapper();

    public void saveCatalog(Catalog catalog) throws Exception {
        Files.createDirectories(Paths.get("metadata"));
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File(catalogPath), catalog);
    }

    public Catalog loadCatalog() throws Exception {
        return mapper.readValue(new File(catalogPath), Catalog.class);
    }
}
