package com.hm.outfitrecommendation.service;

import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitRequest;

public interface AiService {
    Double getAverageRatingForUserHistory(Item item, String customerEmail);
    Double getAverageRatingForItemsComparison(Item itemA, Item itemB);
    void updateUserHistoryWithFeedbackInformation(OutfitRequest feedbackRequest);
}
