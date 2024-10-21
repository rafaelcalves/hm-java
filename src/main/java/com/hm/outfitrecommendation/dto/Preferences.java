package com.hm.outfitrecommendation.dto;

import com.hm.outfitrecommendation.model.classification.Mood;
import com.hm.outfitrecommendation.model.classification.Occasion;
import com.hm.outfitrecommendation.model.classification.Season;
import com.hm.outfitrecommendation.model.classification.Style;

public record Preferences(
        Occasion occasion,
        Mood mood,
        Style style,
        Season season,
        Double minBudget,
        Double maxBudget
) {
}
