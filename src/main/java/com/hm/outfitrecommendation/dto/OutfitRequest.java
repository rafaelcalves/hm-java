package com.hm.outfitrecommendation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OutfitRequest (
        @NotBlank(message = "{jakarta.validation.constraints.NotBlank.message.customerEmail}")
        String customerEmail,
        @Valid @NotNull(message = "{jakarta.validation.constraints.NotNull.message.preferences}")
        Preferences preferences
){
}
