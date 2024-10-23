package com.hm.outfitrecommendation.service.mock;

import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.service.AiService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MockAiService implements AiService {
    @Override
    public Double getAverageRatingForUserHistory(Item item, String customerEmail) {
        return new Random().nextDouble();
    }

    @Override
    public Double getAverageRatingForItemsComparison(Item itemA, Item itemB) {
        return new Random().nextDouble();
    }

    @Override
    public void updateUserHistoryWithFeedbackInformation(OutfitRequest feedbackRequest) {
        // should update user history
    }
}
