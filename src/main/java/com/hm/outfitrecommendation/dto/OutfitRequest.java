package com.hm.outfitrecommendation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OutfitRequest (
        @NotBlank(message = "{notBlank.message.customerEmail}")
        String customerEmail,
        @Valid @NotNull(message = "{notNull.message.preferences}")
        Preferences preferences,
        @Valid
        Feedback feedback
){
}
