package com.hm.outfitrecommendation.dto;

import com.hm.outfitrecommendation.model.Customer;

public record OutfitRequest (
        Customer customer,
        Preferences preferences
){
}
