package com.hm.outfitrecommendation.dto;

import com.hm.outfitrecommendation.dto.classification.Mood;
import com.hm.outfitrecommendation.dto.classification.Occasion;
import com.hm.outfitrecommendation.dto.classification.Season;
import com.hm.outfitrecommendation.dto.classification.Style;
import jakarta.validation.constraints.NotNull;

public record Preferences(
        @NotNull(message = "{notNull.message.occasion}")
        Occasion occasion,
        Mood mood,
        Style style,
        Season season,
        Budget budget
) {
    public record Budget(Double min, Double max) {

    }
}
