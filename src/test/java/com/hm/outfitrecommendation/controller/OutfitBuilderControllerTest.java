package com.hm.outfitrecommendation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.outfitrecommendation.config.ObjectMapperConfig;
import com.hm.outfitrecommendation.dto.OutfitRequest;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import com.hm.outfitrecommendation.service.OutfitBuilderService;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestWithResources
@WebMvcTest(OutfitBuilderController.class)
@ActiveProfiles("test")
public class OutfitBuilderControllerTest {
    @MockBean
    private OutfitBuilderService outfitBuilderService;

    @Autowired
    private MockMvc mockMvc;

    @WithJacksonMapper
    public final ObjectMapper jsonMapper = ObjectMapperConfig.getObjectMapper();

    @GivenJsonResource("/com/hm/outfitrecommendation/controller/outfit-request.json")
    public OutfitRequest outfitRequest;

    @GivenTextResource("/com/hm/outfitrecommendation/controller/outfit-request.json")
    public String outfitRequestJson;

    @GivenJsonResource("/com/hm/outfitrecommendation/controller/outfit-response.json")
    public OutfitResponse outfitResponse;

    @GivenTextResource("/com/hm/outfitrecommendation/controller/outfit-response.json")
    public String outfitResponseJson;

    @GivenJsonResource("/com/hm/outfitrecommendation/controller/feedback-request.json")
    public OutfitRequest feedbackRequest;

    @GivenTextResource("/com/hm/outfitrecommendation/controller/feedback-request.json")
    public String feedbackRequestJson;

    @GivenTextResource("/com/hm/outfitrecommendation/controller/invalid-feedback-request.json")
    public String invalidFeedbackRequestJson;

    @GivenTextResource("/com/hm/outfitrecommendation/controller/invalid-outfit-request.json")
    public String invalidOutfitRequestJson;

    @Test
    public void shouldCallOutfitBuilderServiceBasedOnOutfitRequest() throws Exception
    {
        given(outfitBuilderService.getRecommendedItems(outfitRequest)).willReturn(outfitResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/outfit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(outfitRequestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(outfitResponseJson));
    }

    @Test
    public void shouldReturnProblemBasedOnOutfitRequest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/outfit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOutfitRequestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        jsonPath("$.title").value(HttpStatus.BAD_REQUEST.getReasonPhrase()),
                        jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.detail").value(matchesPattern("^(?=.*occasion_error)(?=.*customer_error).*")),
                        jsonPath("$.instance").value("/outfit"))
                .andReturn();

        verify(outfitBuilderService, never()).getRecommendedItems(any(OutfitRequest.class));
    }

    @Test
    public void shouldCallOutfitBuilderServiceBasedOnFeedbackRequest() throws Exception
    {
        given(outfitBuilderService.getRecommendedItems(feedbackRequest)).willReturn(outfitResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/outfit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackRequestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(outfitResponseJson));
    }
}
