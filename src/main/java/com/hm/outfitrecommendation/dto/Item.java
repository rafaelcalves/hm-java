package com.hm.outfitrecommendation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hm.outfitrecommendation.dto.classification.*;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.openqa.selenium.support.Colors;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

public record Item(
        @JsonProperty("_id")
        @NotBlank(message = "{notBlank.message.id}")
        String id,
        @NotBlank(message = "{notBlank.message.sku}")
        String sku,
        @NotBlank(message = "{notBlank.message.description}")
        String description,
        @NotBlank(message = "{notBlank.message.name}")
        String name,
        @NotNull(message = "{notNull.message.category}")
        Category category,
        SubCategory subCategory,
        @Nullable @Min(value = 0, message = "{min.message.price}")
        Double price,
        @Nullable @Min(value = 0, message = "{min.message.stock}")
        Integer stock,
        Colors color,
        List<Occasion> occasions,
        List<Mood> moods,
        List<Style> styles,
        List<Season> seasons,
        List<URI> images,
        LocalDate releaseDate
) {
}