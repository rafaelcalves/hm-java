package com.hm.outfitrecommendation.dto;

import com.hm.outfitrecommendation.model.Customer;

import java.util.List;

public record FeedbackRequest(
        Customer customer,
        Preferences preferences,
        List<ItemFeedback> items,
        Boolean outfitSubmission
) {
}
