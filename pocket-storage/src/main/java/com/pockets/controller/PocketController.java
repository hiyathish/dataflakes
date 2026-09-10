package com.pockets.controller;

import com.pockets.service.PocketStorageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pockets/splitter")
public class PocketController {

    private final PocketStorageService service;

    public PocketController(PocketStorageService service) {
        this.service = service;
    }

    @PostMapping("/split")
    public String split(@RequestParam String filePath) throws Exception {
        service.splitFile(filePath);
        return "File split successfully";
    }

    @PostMapping("/reconstruct")
    public String reconstruct(@RequestParam String outputFile) throws Exception {
        service.reconstruct(outputFile);
        return "File reconstructed";
    }
}
