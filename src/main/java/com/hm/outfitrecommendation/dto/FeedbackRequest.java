package com.hm.outfitrecommendation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FeedbackRequest(
        @NotBlank(message = "{jakarta.validation.constraints.NotBlank.message.customerEmail}")
        String customerEmail,
        @Valid @NotNull(message = "{jakarta.validation.constraints.NotNull.message.preferences}")
        Preferences preferences,
        @NotNull(message = "{jakarta.validation.constraints.NotNull.message.items}")
        @Size(min = 1, message = "{jakarta.validation.constraints.Size.message.items}")
        List<ItemFeedback> items,
        @NotNull(message = "{jakarta.validation.constraints.NotNull.message.outfitSubmission}")
        Boolean outfitSubmission
) {
}
