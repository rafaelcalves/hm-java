package com.hm.outfitrecommendation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.outfitrecommendation.config.ObjectMapperConfig;
import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import com.hm.outfitrecommendation.service.impl.OutfitBuilderServiceImpl;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.management.DescriptorKey;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestWithResources
@ExtendWith(MockitoExtension.class)
public class OutfitBuilderServiceTest {
    @InjectMocks
    private OutfitBuilderServiceImpl outfitBuilderService;

    @Mock
    private AiService aiService;
    @Mock
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
    @GivenJsonResource("com/hm/outfitrecommendation/service/item.json")
    public Item item;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(outfitBuilderService,"userHistorybaseRating", BigDecimal.valueOf(0.6));
        ReflectionTestUtils.setField(outfitBuilderService,"unitsPerCategory", 1);
    }

    @Test
    @DisplayName("When all items have the same result on AiService, Should return the first item of each category")
    void whenAiServiceResultsAreTheSameShouldReturnFirstItemOfEachCategory() {
        when(stockService.getMatchingAvailableItems(outfitRequest.preferences())).thenReturn(items);
        when(aiService.getAverageRatingForUserHistory(any(Item.class), eq(outfitRequest.customerEmail()))).thenReturn(BigDecimal.valueOf(0.7));
        when(aiService.getAverageRatingForItemsComparison(any(Item.class),any(Item.class))).thenReturn(BigDecimal.ONE);

        OutfitResponse recommendedItems = outfitBuilderService.getRecommendedItems(outfitRequest);
        assertThat(recommendedItems.items()).containsExactlyInAnyOrderElementsOf(outfitResponse.items());
    }

    @Test
    @DisplayName("When a item is filtered by AiService, Should return the first item of each category except the filtered one")
    void whenAiServiceFiltersAnItemShouldReturnFirstItemOfEachCategoryExceptIt() {
        when(stockService.getMatchingAvailableItems(outfitRequest.preferences())).thenReturn(items);
        when(aiService.getAverageRatingForUserHistory(any(Item.class), eq(outfitRequest.customerEmail()))).thenReturn(BigDecimal.valueOf(0.7));
        when(aiService.getAverageRatingForUserHistory(item, outfitRequest.customerEmail())).thenReturn(BigDecimal.ZERO);
        when(aiService.getAverageRatingForItemsComparison(any(Item.class),any(Item.class))).thenReturn(BigDecimal.ONE);

        OutfitResponse recommendedItems = outfitBuilderService.getRecommendedItems(outfitRequest);
        assertThat(recommendedItems.items()).containsExactlyInAnyOrderElementsOf(outfitResponseFiltered.items());
    }

    @Test
    @DisplayName("When a item is bad rated by AiService, Should return the first item of each category except the bad rated one")
    void whenAiServiceBadRatesAnItemShouldReturnFirstItemOfEachCategoryExceptIt() {
        when(stockService.getMatchingAvailableItems(outfitRequest.preferences())).thenReturn(items);
        when(aiService.getAverageRatingForUserHistory(any(Item.class), eq(outfitRequest.customerEmail()))).thenReturn(BigDecimal.valueOf(0.7));
        when(aiService.getAverageRatingForItemsComparison(any(Item.class),any(Item.class))).thenReturn(BigDecimal.ONE);
        when(aiService.getAverageRatingForItemsComparison(eq(item),any(Item.class))).thenReturn(BigDecimal.ZERO);

        OutfitResponse recommendedItems = outfitBuilderService.getRecommendedItems(outfitRequest);
        assertThat(recommendedItems.items()).containsExactlyInAnyOrderElementsOf(outfitResponseFiltered.items());
    }
}
