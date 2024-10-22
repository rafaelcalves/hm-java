package com.hm.outfitrecommendation.controller;

import com.hm.outfitrecommendation.dto.FeedbackRequest;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import com.hm.outfitrecommendation.service.OutfitBuilderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outfit")
public class OutfitBuilderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutfitBuilderController.class);

    private final OutfitBuilderService outfitBuilderService;

    public OutfitBuilderController(OutfitBuilderService outfitBuilderService) {
        this.outfitBuilderService = outfitBuilderService;
    }

    @PostMapping
    public OutfitResponse buildOutfit(@Valid @RequestBody OutfitRequest outfitRequest) {
        return outfitBuilderService.getRecommendedItems(outfitRequest);
    }

    @PostMapping("/feedback")
    public OutfitResponse buildOutfit(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        return outfitBuilderService.getRecommendedItems(feedbackRequest);
    }
}
