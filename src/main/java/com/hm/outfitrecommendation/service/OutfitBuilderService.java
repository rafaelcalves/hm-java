package com.hm.outfitrecommendation.service;

import com.hm.outfitrecommendation.dto.FeedbackRequest;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;

public interface OutfitBuilderService {
    OutfitResponse getRecommendedItems(OutfitRequest outfitRequest);
    OutfitResponse getRecommendedItems(FeedbackRequest feedbackRequest);
}
