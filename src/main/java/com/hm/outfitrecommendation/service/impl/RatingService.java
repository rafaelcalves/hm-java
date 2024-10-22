package com.hm.outfitrecommendation.service.impl;

import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.ItemFeedback;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.service.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {
    private final AiService aiService;

    @Value("${outfit.nps-base}")
    private Double npsCalculationBase;
    @Value("${outfit.base-rating}")
    private Double userHistorybaseRating;

    public RatingService(AiService aiService) {
        this.aiService = aiService;
    }

    public boolean validateUserHistoryRating(OutfitRequest outfitRequest, Item item) {
        var itemFeedbackRatingAdjustment = getItemFeedbackRatingAdjustment(outfitRequest, item);

        Double finalBaseRatingForItem = userHistorybaseRating + -1*itemFeedbackRatingAdjustment;
        Double itemRating = aiService.getAverageRatingForUserHistory(item, outfitRequest.customerEmail());
        return itemRating >= finalBaseRatingForItem;
    }

    private double getItemFeedbackRatingAdjustment(OutfitRequest outfitRequest, Item item) {
        var itemFeedbackAdjustment = 0.0;
        if(outfitRequest.feedback() != null)
            itemFeedbackAdjustment = outfitRequest.feedback()
                    .items()
                    .stream()
                    .filter(feedback -> item.equals(feedback.item()))
                    .findFirst()
                    .map(this::calculateNpsRating)
                    .orElse(0.0);
        return itemFeedbackAdjustment;
    }

    private Double calculateNpsRating(ItemFeedback feedback) {
        if(feedback.note() == null || feedback.note() >= 0.7 && feedback.note() < 0.9) return 0.0;
        if(feedback.note() >= 0.9) return npsCalculationBase - (1 - feedback.note());
        return (feedback.note() - 1) * npsCalculationBase;
    }

    public Map<Item, Double> rateItems(List<Item> aiFilteredItems) {
        final Map<Item, Double> itemRatings = new HashMap<>();
        aiFilteredItems.parallelStream()
                .forEach(itemA -> itemRatings.put(itemA, aiFilteredItems.parallelStream()
                        .map(itemB -> aiService.getAverageRatingForItemsComparison(itemA, itemB))
                        .reduce(0.0, Double::sum)));
        return itemRatings;
    }
}
