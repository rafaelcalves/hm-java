package com.hm.outfitrecommendation.dto;

import com.hm.outfitrecommendation.dto.classification.Mood;
import com.hm.outfitrecommendation.dto.classification.Occasion;
import com.hm.outfitrecommendation.dto.classification.Season;
import com.hm.outfitrecommendation.dto.classification.Style;
import jakarta.validation.constraints.NotNull;
import org.openqa.selenium.support.Colors;

public record Preferences(
        @NotNull(message = "{notNull.message.occasion}")
        Occasion occasion,
        Mood mood,
        Style style,
        Season season,
        Colors color,
        Budget budget
) {
    public record Budget(Double min, Double max) {

    }
}
