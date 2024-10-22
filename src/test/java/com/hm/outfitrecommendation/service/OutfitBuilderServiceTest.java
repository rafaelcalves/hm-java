package com.hm.outfitrecommendation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.outfitrecommendation.MapFactory;
import com.hm.outfitrecommendation.config.ObjectMapperConfig;
import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import com.hm.outfitrecommendation.service.impl.ClassificationService;
import com.hm.outfitrecommendation.service.impl.OutfitBuilderService;
import com.hm.outfitrecommendation.service.impl.RatingService;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@TestWithResources
@ExtendWith(MockitoExtension.class)
public class OutfitBuilderServiceTest {
    @WithJacksonMapper
    public final ObjectMapper jsonMapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("com/hm/outfitrecommendation/service/outfit-request.json")
    public OutfitRequest outfitRequest;
    @GivenJsonResource("com/hm/outfitrecommendation/service/feedback-request.json")
    public OutfitRequest feedbackRequest;
    @GivenJsonResource("com/hm/outfitrecommendation/service/feedback-request-nosubmission.json")
    public OutfitRequest feedbackRequestNoSubmission;
    @GivenJsonResource("com/hm/outfitrecommendation/service/outfit-response.json")
    public OutfitResponse outfitResponse;
    @GivenJsonResource("com/hm/outfitrecommendation/service/items.json")
    public List<Item> items;
    @InjectMocks
    private OutfitBuilderService outfitBuilderService;
    @Mock
    private RatingService ratingService;
    @Mock
    private ClassificationService classificationService;
    @Mock
    private AiService aiService;
    @Mock
    private StockService stockService;

    @Test
    @DisplayName("When request doesn't have feedback, flow should be normal")
    void whenNotFeedBackShouldFollowTheNormalFlow() {
        Map<Item, Double> map = MapFactory.createMap(items);
        when(stockService.getMatchingAvailableItems(outfitRequest.preferences())).thenReturn(items);
        when(ratingService.validateUserHistoryRating(eq(outfitRequest), any(Item.class))).thenReturn(true);
        when(ratingService.rateItems(items)).thenReturn(map);
        when(classificationService.classifyItems(map)).thenReturn(outfitResponse.items());

        OutfitResponse result = outfitBuilderService.getRecommendedItems(outfitRequest);
        assertEquals(outfitResponse, result);
        verify(aiService, never()).updateUserHistoryWithFeedbackInformation(outfitRequest);
    }

    @Test
    @DisplayName("When request have feedback and submission, flow should stop")
    void whenFeedBackShouldStopAndReturnReceivedItems() {
        Map<Item, Double> map = MapFactory.createMap(items);
        doNothing().when(aiService).updateUserHistoryWithFeedbackInformation(feedbackRequestNoSubmission);
        when(stockService.getMatchingAvailableItems(feedbackRequestNoSubmission.preferences())).thenReturn(items);
        when(ratingService.validateUserHistoryRating(eq(feedbackRequestNoSubmission), any(Item.class))).thenReturn(true);
        when(ratingService.rateItems(items)).thenReturn(map);
        when(classificationService.classifyItems(map)).thenReturn(outfitResponse.items());

        OutfitResponse result = outfitBuilderService.getRecommendedItems(feedbackRequestNoSubmission);
        assertEquals(outfitResponse, result);

        verify(aiService, times(1)).updateUserHistoryWithFeedbackInformation(feedbackRequestNoSubmission);
    }

    @Test
    @DisplayName("When request have feedback but no submission, flow should be reprocessed with feedback")
    void whenFeedBackShouldFollowReprocessedFeedbackFlow() {
        doNothing().when(aiService).updateUserHistoryWithFeedbackInformation(feedbackRequest);

        OutfitResponse result = outfitBuilderService.getRecommendedItems(feedbackRequest);
        assertEquals(outfitResponse, result);

        verify(aiService, times(1)).updateUserHistoryWithFeedbackInformation(feedbackRequest);
        verify(stockService, never()).getMatchingAvailableItems(feedbackRequest.preferences());
    }
}
