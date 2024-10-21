package com.hm.outfitrecommendation.service;

import com.hm.outfitrecommendation.model.Customer;
import com.hm.outfitrecommendation.model.Item;

import java.math.BigDecimal;

public interface AiService {
    BigDecimal getAverageRatingForUserHistory(Item item, Customer customer);
    BigDecimal getAverageRatingForItemsComparison(Item itemA, Item itemB);
}
