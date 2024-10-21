package com.hm.outfitrecommendation.dto;

import com.hm.outfitrecommendation.model.Item;

import java.util.List;

public record OutfitResponse(
        List<Item> items
) {
}
