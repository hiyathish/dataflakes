package com.pockets.controller;

import com.pockets.service.PocketReconstructService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pockets/reconstruct")
public class PocketReconstructController {

    private final PocketReconstructService reconstructService;

    public PocketReconstructController(PocketReconstructService reconstructService) {
        this.reconstructService = reconstructService;
    }

    @PostMapping("/reconstruct")
    public String reconstruct(@RequestParam String outputFile) throws Exception {
        reconstructService.reconstruct(outputFile);
        return "File reconstructed successfully: " + outputFile;
    }
}
