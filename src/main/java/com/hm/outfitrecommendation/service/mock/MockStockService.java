package com.hm.outfitrecommendation.service.mock;

import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.Preferences;
import com.hm.outfitrecommendation.service.StockService;
import io.hosuaby.inject.resources.spring.JsonResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockStockService implements StockService {
    @JsonResource("/io/hosuaby/junit/jupiter/sponge-bob.json")
    private List<Item> mockFilteredQueryResult;

    @Override
    public List<Item> getMatchingAvailableItems(Preferences preferences) {
        return mockFilteredQueryResult;
    }
}
