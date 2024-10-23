package com.hm.outfitrecommendation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Feedback (
        @NotNull(message = "{notNull.message.items}")
        @Size(min = 1, message = "{size.message.items}")
        @Valid
        List<ItemFeedback> items,
        @NotNull(message = "{notNull.message.outfitSubmission}")
        Boolean outfitSubmission
) {
}
