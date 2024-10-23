package com.hm.outfitrecommendation.service.mock;

import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.Preferences;
import com.hm.outfitrecommendation.service.StockService;
import io.hosuaby.inject.resources.spring.JsonResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockStockService implements StockService {
    @JsonResource("/com/hm/outfitrecommendation/service/mock/items.json")
    private List<Item> mockFilteredQueryResult;

    private static boolean matchPreferences(Item item, Preferences preferences) {
        return item.stock() != null && item.stock() > 0 &&
                item.occasions().contains(preferences.occasion()) &&
                (preferences.budget() == null || preferences.budget().min() <= item.price() &&
                        preferences.budget().max() >= item.price()) &&
                (preferences.color() == null || item.color().equals(preferences.color())) &&
                (preferences.mood() == null || item.moods().contains(preferences.mood())) &&
                (preferences.season() == null || item.seasons().contains(preferences.season())) &&
                (preferences.style() == null || item.styles().contains(preferences.style()));
    }

    @Override
    public List<Item> getMatchingAvailableItems(Preferences preferences) {
        return mockFilteredQueryResult.stream().filter(item -> matchPreferences(item, preferences)).toList();
    }
}
