package com.hm.outfitrecommendation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemFeedback(
        @Valid @NotNull(message = "{jakarta.validation.constraints.NotNull.message.item}")
        Item item,
        @Min(value = 0, message = "{jakarta.validation.constraints.Min.message.note}")
        @Max(value = 1, message = "{jakarta.validation.constraints.Max.message.note}")
        Double note
) {
}
