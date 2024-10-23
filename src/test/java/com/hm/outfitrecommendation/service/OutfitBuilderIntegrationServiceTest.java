package com.hm.outfitrecommendation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.outfitrecommendation.config.ObjectMapperConfig;
import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestWithResources
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OutfitBuilderIntegrationServiceTest {
    @Autowired
    private OutfitBuilderService outfitBuilderService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private ClassificationService classificationService;

    @MockBean
    private AiService aiService;
    @MockBean
    private StockService stockService;

    @WithJacksonMapper
    public final ObjectMapper jsonMapper = ObjectMapperConfig.getObjectMapper();

    @GivenJsonResource("com/hm/outfitrecommendation/service/outfit-request.json")
    public OutfitRequest outfitRequest;
    @GivenJsonResource("com/hm/outfitrecommendation/service/items.json")
    public List<Item> items;
    @GivenJsonResource("com/hm/outfitrecommendation/service/outfit-response.json")
    public OutfitResponse outfitResponse;
    @GivenJsonResource("com/hm/outfitrecommendation/service/outfit-response-filtered.json")
    public OutfitResponse outfitResponseFiltered;
    @GivenJsonResource("com/hm/outfitrecommendation/service/item-detractor.json")
    public Item item;

    @Test
    @DisplayName("When all items have the same result on AiService, Should return the first item of each category")
    void whenAiServiceResultsAreTheSameShouldReturnFirstItemOfEachCategory() {
        when(stockService.getMatchingAvailableItems(outfitRequest.preferences())).thenReturn(items);
        when(aiService.getAverageRatingForUserHistory(any(Item.class), eq(outfitRequest.customerEmail()))).thenReturn(0.7);
        when(aiService.getAverageRatingForItemsComparison(any(Item.class),any(Item.class))).thenReturn(1.0);

        OutfitResponse recommendedItems = outfitBuilderService.getRecommendedItems(outfitRequest);
        assertThat(recommendedItems.items()).containsExactlyInAnyOrderElementsOf(outfitResponse.items());
    }

    @Test
    @DisplayName("When a item is filtered by AiService, Should return the first item of each category except the filtered one")
    void whenAiServiceFiltersAnItemShouldReturnFirstItemOfEachCategoryExceptIt() {
        when(stockService.getMatchingAvailableItems(outfitRequest.preferences())).thenReturn(items);
        when(aiService.getAverageRatingForUserHistory(any(Item.class), eq(outfitRequest.customerEmail()))).thenReturn(0.7);
        when(aiService.getAverageRatingForUserHistory(item, outfitRequest.customerEmail())).thenReturn(0.0);
        when(aiService.getAverageRatingForItemsComparison(any(Item.class),any(Item.class))).thenReturn(1.0);

        OutfitResponse recommendedItems = outfitBuilderService.getRecommendedItems(outfitRequest);
        assertThat(recommendedItems.items()).containsExactlyInAnyOrderElementsOf(outfitResponseFiltered.items());
    }

    @Test
    @DisplayName("When a item is bad rated by AiService, Should return the first item of each category except the bad rated one")
    void whenAiServiceBadRatesAnItemShouldReturnFirstItemOfEachCategoryExceptIt() {
        when(stockService.getMatchingAvailableItems(outfitRequest.preferences())).thenReturn(items);
        when(aiService.getAverageRatingForUserHistory(any(Item.class), eq(outfitRequest.customerEmail()))).thenReturn(0.7);
        when(aiService.getAverageRatingForItemsComparison(any(Item.class),any(Item.class))).thenReturn(1.0);
        when(aiService.getAverageRatingForItemsComparison(eq(item),any(Item.class))).thenReturn(0.0);

        OutfitResponse recommendedItems = outfitBuilderService.getRecommendedItems(outfitRequest);
        assertThat(recommendedItems.items()).containsExactlyInAnyOrderElementsOf(outfitResponseFiltered.items());
    }
}
