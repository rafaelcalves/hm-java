package com.hm.outfitrecommendation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.outfitrecommendation.MapFactory;
import com.hm.outfitrecommendation.config.ObjectMapperConfig;
import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.OutfitResponse;
import com.hm.outfitrecommendation.service.impl.ClassificationService;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestWithResources
@ExtendWith(MockitoExtension.class)
public class ClassificationServiceTest {
    private final ClassificationService classificationService = new ClassificationService();

    @WithJacksonMapper
    public ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("com/hm/outfitrecommendation/service/items.json")
    public List<Item> items;
    @GivenJsonResource("com/hm/outfitrecommendation/service/outfit-response.json")
    public OutfitResponse outfitResponse;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(classificationService, "unitsPerCategory", 1);
    }

    @Test
    @DisplayName("When itemMap has ratings should return only the first item of each category")
    void shouldReturnListWithFirstCategoriesItems() {
        List<Item> result = classificationService.classifyItems(MapFactory.createMap(items));

        assertThat(result).containsExactlyInAnyOrderElementsOf(outfitResponse.items());
    }
}
