package com.hm.outfitrecommendation.model;

import com.hm.outfitrecommendation.model.classification.Category;
import com.hm.outfitrecommendation.model.classification.SubCategory;
import com.hm.outfitrecommendation.model.classification.Mood;
import com.hm.outfitrecommendation.model.classification.Occasion;
import com.hm.outfitrecommendation.model.classification.Season;
import com.hm.outfitrecommendation.model.classification.Style;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class Item {
    private String id;
    private String sku;
    private String description;
    private String name;
    private Category category;
    private SubCategory subCategory;
    private Double price;
    private Integer stock;
    private Color color;
    private List<Occasion> occasions;
    private List<Mood> moods;
    private List<Style> styles;
    private List<Season> seasons;
    private List<URI> images;

    public Item(String id, String sku, String description, String name, Category category, SubCategory subCategory,
                Double price, Integer stock, Color color, List<Occasion> occasions, List<Mood> moods,
                List<Style> styles, List<Season> seasons, List<URI> images) {
        this.id = id;
        this.sku = sku;
        this.description = description;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.stock = stock;
        this.color = color;
        this.occasions = occasions;
        this.moods = moods;
        this.styles = styles;
        this.seasons = seasons;
        this.images = images;

        this.validate();
    }

    public void validate() {
        if(this.category == null || !this.category.equals(this.subCategory.getCategory()))
            throw new IllegalArgumentException("Sub category does not match category");
        if(this.stock < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
        if(this.price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
    }
}