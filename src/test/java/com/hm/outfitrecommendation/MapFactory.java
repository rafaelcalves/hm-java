package com.hm.outfitrecommendation;

import com.hm.outfitrecommendation.dto.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFactory {
    public static Map<Item, Double> createMap(List<Item> items) {
        Map<Item, Double> map = new HashMap<>();
        items.parallelStream().forEach(item -> map.put(item, 1 - 0.1 * Double.parseDouble(item.id())));

        return map;
    }
}
