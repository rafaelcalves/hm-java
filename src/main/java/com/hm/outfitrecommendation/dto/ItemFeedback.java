package com.hm.outfitrecommendation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemFeedback(
        @Valid @NotNull(message = "{notNull.message.item}")
        Item item,
        @Min(value = 0, message = "{min-max.message.note}")
        @Max(value = 1, message = "{min-max.message.note}")
        Double note
) {
}
