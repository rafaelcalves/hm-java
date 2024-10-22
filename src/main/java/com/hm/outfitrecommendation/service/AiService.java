package com.hm.outfitrecommendation.service;

import com.hm.outfitrecommendation.dto.Item;

import java.math.BigDecimal;

public interface AiService {
    BigDecimal getAverageRatingForUserHistory(Item item, String customerEmail);
    BigDecimal getAverageRatingForItemsComparison(Item itemA, Item itemB);
}
