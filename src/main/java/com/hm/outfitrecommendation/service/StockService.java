package com.hm.outfitrecommendation.service;

import com.hm.outfitrecommendation.dto.Preferences;
import com.hm.outfitrecommendation.dto.Item;

import java.util.List;

public interface StockService {
    List<Item> getMatchingAvailableItems(Preferences preferences);
}
