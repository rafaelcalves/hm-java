package com.hm.outfitrecommendation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outfit")
public class OutfitController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutfitController.class);

    @GetMapping
    public void getOutfit(){
        LOGGER.info("CONTROLLER");
    }
}
