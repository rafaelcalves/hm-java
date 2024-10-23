package com.hm.outfitrecommendation.service;

import com.hm.outfitrecommendation.dto.Item;
import com.hm.outfitrecommendation.dto.classification.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassificationService {

    @Value("${outfit.units-per-category}")
    private Integer unitsPerCategory;

    public List<Item> classifyItems(Map<Item, Double> itemRatings) {
        Map<Category,Integer> categoryCounter = new HashMap<>();
        List<Item> result = new ArrayList<>();

        itemRatings.entrySet()
                .stream()
                .sorted(getRatingComparator().thenComparing(getItemComparator()))
                .forEach(entry -> classifyItem(entry.getKey(), categoryCounter, result));
        return result;
    }

    private static Comparator<Map.Entry<Item, Double>> getRatingComparator() {
        return Map.Entry.comparingByValue(Comparator.reverseOrder());
    }

    private static Comparator<Map.Entry<Item, Double>> getItemComparator() {
        return Map.Entry.comparingByKey(Comparator.comparing(Item::releaseDate).reversed());
    }

    private void classifyItem(Item item, Map<Category, Integer> categoryCounter, List<Item> result) {
        Integer counter = categoryCounter.getOrDefault(item.category(),0);
        if(counter < unitsPerCategory) {
            result.add(item);
            categoryCounter.put(item.category(), counter + 1);
        }
    }
}
