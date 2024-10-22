package com.hm.outfitrecommendation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.outfitrecommendation.config.ObjectMapperConfig;
import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.service.impl.RatingService;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestWithResources
@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
    @WithJacksonMapper
    public static ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("com/hm/outfitrecommendation/service/outfit-request.json")
    public static OutfitRequest outfitRequest;
    @GivenJsonResource("com/hm/outfitrecommendation/service/feedback-request.json")
    public static OutfitRequest feedbackRequest;
    @GivenJsonResource("com/hm/outfitrecommendation/service/item-detractor.json")
    public static Item itemDetractor;
    @GivenJsonResource("com/hm/outfitrecommendation/service/item-promoter.json")
    public static Item itemPromoter;
    @GivenJsonResource("com/hm/outfitrecommendation/service/item-passive.json")
    public static Item itemPassive;
    @GivenJsonResource("com/hm/outfitrecommendation/service/item-passive-null.json")
    public static Item itemPassiveNull;
    @InjectMocks
    private RatingService ratingService;
    @Mock
    private AiService aiService;

    public static Stream<Arguments> getNoteValues() {
        return Stream.of(
                Arguments.of("PASSIVE", "NOT CHANGE", itemPassive, 0.6, true),
                Arguments.of("PASSIVE (NULL)", "NOT CHANGE", itemPassiveNull, 0.8, true),
                Arguments.of("PROMOTER", "INCREASE", itemPromoter, 0.5, true),
                Arguments.of("DETRACTOR", "DECREASE", itemDetractor, 0.7, false)
        );
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ratingService, "npsCalculationBase", 0.3);
        ReflectionTestUtils.setField(ratingService, "userHistorybaseRating", 0.6);
    }

    @Test
    @DisplayName("When item rating is bigger than base rating then return true")
    void whenRatingBiggerThanBaseRatingShouldReturnTrue() {
        when(aiService.getAverageRatingForUserHistory(itemDetractor, outfitRequest.customerEmail())).thenReturn(1.0);
        boolean result = ratingService.validateUserHistoryRating(outfitRequest, itemDetractor);
        assertTrue(result);
    }

    @Test
    @DisplayName("When item rating is lower than base rating then return false")
    void whenRatingLowerThanBaseRatingShouldReturnFalse() {
        when(aiService.getAverageRatingForUserHistory(itemDetractor, outfitRequest.customerEmail())).thenReturn(0.1);
        boolean result = ratingService.validateUserHistoryRating(outfitRequest, itemDetractor);
        assertFalse(result);
    }

    @ParameterizedTest(name = "{index} - When item feedback note is {0} than base rating should {1}")
    @MethodSource("getNoteValues")
    void whenPassiveFeedbackThanBaseRatingDoesNotChange(String name, String baseDescription, Item item, Double rating, boolean approved) {
        when(aiService.getAverageRatingForUserHistory(item, feedbackRequest.customerEmail())).thenReturn(rating);
        boolean result = ratingService.validateUserHistoryRating(feedbackRequest, item);
        assertEquals(approved, result);
    }
}
