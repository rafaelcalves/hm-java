package com.hm.outfitrecommendation.dto;

import com.hm.outfitrecommendation.model.Item;

public record ItemFeedback(
        Item item,
        Double note
) {
}
