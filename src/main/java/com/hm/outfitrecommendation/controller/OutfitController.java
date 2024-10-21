package com.hm.outfitrecommendation.controller;

import com.hm.outfitrecommendation.dto.FeedbackRequest;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import com.hm.outfitrecommendation.service.OutfitBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outfit")
public class OutfitController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutfitController.class);

    private final OutfitBuilderService outfitBuilderService;

    public OutfitController(OutfitBuilderService outfitBuilderService) {
        this.outfitBuilderService = outfitBuilderService;
    }

    @GetMapping
    public OutfitResponse getOutfit(OutfitRequest outfitRequest) {
        return outfitBuilderService.getRecommendedItems(outfitRequest);
    }

    @GetMapping("/feedback")
    public OutfitResponse getOutfit(FeedbackRequest feedbackRequest) {
        return outfitBuilderService.getRecommendedItems(feedbackRequest);
    }
}
