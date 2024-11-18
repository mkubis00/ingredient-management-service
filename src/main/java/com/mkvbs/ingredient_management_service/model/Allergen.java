package com.mkvbs.ingredient_management_service.model;

import com.mkvbs.ingredient_management_service.resource.model.AllergenValues;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Allergen {
    GLUTEN(AllergenValues.GLUTEN),
    SHELLFISH(AllergenValues.SHELLFISH),
    EGG(AllergenValues.EGG),
    FISH(AllergenValues.FISH),
    PEANUTS(AllergenValues.PEANUTS),
    SOY(AllergenValues.SOY),
    MILK(AllergenValues.MILK),
    NUTS(AllergenValues.NUTS),
    CELERY(AllergenValues.CELERY),
    MUSTARD(AllergenValues.MUSTARD),
    SESAME(AllergenValues.SESAME),
    LUPINE(AllergenValues.LUPINE),
    MOLLUSKS(AllergenValues.MOLLUSKS),
    NONE(AllergenValues.NONE);

    private final String allergenDescription;
}
