package com.hm.outfitrecommendation.dto;

import java.util.List;

public record OutfitResponse(
        List<Item> items
) {
}
