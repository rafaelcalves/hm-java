package com.hm.outfitrecommendation.service.impl;

import com.hm.outfitrecommendation.dto.FeedbackRequest;
import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import com.hm.outfitrecommendation.dto.classification.Category;
import com.hm.outfitrecommendation.service.AiService;
import com.hm.outfitrecommendation.service.OutfitBuilderService;
import com.hm.outfitrecommendation.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OutfitBuilderServiceImpl implements OutfitBuilderService {
    private final AiService aiService;
    private final StockService stockService;
    private final BigDecimal userHistorybaseRating;
    private final Integer unitsPerCategory;

    public OutfitBuilderServiceImpl(AiService aiService, StockService stockService,
                                    @Value("${outfit.base-rating}") BigDecimal userHistorybaseRating,
                                    @Value("${outfit.units-per-category}") Integer unitsPerCategory) {
        this.aiService = aiService;
        this.stockService = stockService;
        this.userHistorybaseRating = userHistorybaseRating;
        this.unitsPerCategory = unitsPerCategory;
    }

    @Override
    public OutfitResponse getRecommendedItems(OutfitRequest outfitRequest) {
        List<Item> aiFilteredItems = getMatchingItems(outfitRequest);
        Map<Item, BigDecimal> itemRatings = rateItems(aiFilteredItems);
        List<Item> result = classifyItems(itemRatings);

        return new OutfitResponse(result);
    }

    private List<Item> getMatchingItems(OutfitRequest outfitRequest) {
        return stockService.getMatchingAvailableItems(outfitRequest.preferences()).parallelStream()
                .filter(item -> aiService.getAverageRatingForUserHistory(item, outfitRequest.customerEmail())
                        .compareTo(userHistorybaseRating) >= 0)
                .toList();
    }

    private Map<Item, BigDecimal> rateItems(List<Item> aiFilteredItems) {
        final Map<Item, BigDecimal> itemRatings = new HashMap<>();
        aiFilteredItems.parallelStream()
                .forEach(itemA -> itemRatings.put(itemA, aiFilteredItems.parallelStream()
                        .map(itemB -> aiService.getAverageRatingForItemsComparison(itemA, itemB))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
        return itemRatings;
    }

    private List<Item> classifyItems(Map<Item, BigDecimal> itemRatings) {
        Map<Category,Integer> categoryCounter = new HashMap<>();
        List<Item> result = new ArrayList<>();

        itemRatings.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> classifyItem(entry.getKey(), categoryCounter, result));
        return result;
    }

    private void classifyItem(Item item, Map<Category, Integer> categoryCounter, List<Item> result) {
        Integer counter = categoryCounter.getOrDefault(item.category(),0);
        if(counter < unitsPerCategory) {
            result.add(item);
            categoryCounter.put(item.category(), counter + 1);
        }
    }

    @Override
    public OutfitResponse getRecommendedItems(FeedbackRequest feedbackRequest) {
        return null;
    }
}
