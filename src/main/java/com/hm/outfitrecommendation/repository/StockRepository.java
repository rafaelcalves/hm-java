package com.hm.outfitrecommendation.repository;

import com.hm.outfitrecommendation.dto.Preferences;
import com.hm.outfitrecommendation.model.Item;

import java.util.List;

public interface StockRepository {
    List<Item> findMatchingAvailableItems(Preferences preferences);
}
