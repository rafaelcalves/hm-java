package com.hm.outfitrecommendation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record ItemFeedback(
        @Valid @NotNull(message = "{notNull.message.item}")
        Item item,
        @Range(min = 0, max = 1, message = "{min-max.message.note}")
        Double note
) {
}
