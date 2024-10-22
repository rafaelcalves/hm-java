package com.hm.outfitrecommendation.dto.classification;

import static com.hm.outfitrecommendation.dto.classification.Category.*;

public enum SubCategory {
        HEAD(ACCESSORIES),
        JEWELRY(ACCESSORIES),
        BELT(ACCESSORIES),
        BAG(ACCESSORIES),
        SHIRT(TOP),
        T_SHIRT(TOP),
        TANKTOP(TOP),
        SWEATSHIRT(TOP),
        TROUSER(BOTTOM),
        SHORTS(BOTTOM),
        SKIRT(BOTTOM),
        DENIM(BOTTOM),
        SANDAL(SHOES),
        SNEAKER(SHOES),
        BOOTS(SHOES),
        HEELS(SHOES),
        JUMPSUIT(ONE_PIECE),
        DRESS(ONE_PIECE),
        ROMPER(ONE_PIECE),
        BODYSUIT(ONE_PIECE),
        JACKET(OUTERWEAR),
        SWEATER(OUTERWEAR),
        COAT(OUTERWEAR),
        BLAZER(OUTERWEAR);

        private Category category;

        SubCategory(Category category) {
            this.category = category;
        }

    public Category getCategory() {
        return category;
    }
}
