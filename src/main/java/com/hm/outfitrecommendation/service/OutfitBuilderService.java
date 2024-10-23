package com.hm.outfitrecommendation.service;

import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.ItemFeedback;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OutfitBuilderService {
    private final AiService aiService;
    private final StockService stockService;
    private final RatingService ratingService;
    private final ClassificationService classificationService;

    public OutfitBuilderService(AiService aiService, StockService stockService,
                                RatingService ratingService, ClassificationService classificationService) {
        this.aiService = aiService;
        this.stockService = stockService;
        this.ratingService = ratingService;
        this.classificationService = classificationService;
    }

    public OutfitResponse getRecommendedItems(OutfitRequest outfitRequest) {
        if (outfitRequest.feedback() != null) {
            aiService.updateUserHistoryWithFeedbackInformation(outfitRequest);
            if (outfitRequest.feedback().outfitSubmission())
                return new OutfitResponse(outfitRequest.feedback()
                        .items()
                        .stream()
                        .map(ItemFeedback::item)
                        .toList());
        }

        List<Item> aiFilteredItems = getMatchingItems(outfitRequest);
        Map<Item, Double> itemRatings = ratingService.rateItems(aiFilteredItems);
        List<Item> result = classificationService.classifyItems(itemRatings);

        return new OutfitResponse(result);
    }

    private List<Item> getMatchingItems(OutfitRequest outfitRequest) {
        return stockService.getMatchingAvailableItems(outfitRequest.preferences()).parallelStream()
                .filter(item -> ratingService.validateUserHistoryRating(outfitRequest, item))
                .toList();
    }
}
