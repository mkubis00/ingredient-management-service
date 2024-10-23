package com.mkvbs.ingredient_management_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "ingredient")
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;
    private final String name;
    private final TypeOfQuantity typeOfQuantity;
    private final Allergen allergen;

    public Ingredient(String name, TypeOfQuantity typeOfQuantity, Allergen allergen) {
        this.id = null;
        this.name = name;
        this.typeOfQuantity = typeOfQuantity;
        this.allergen = allergen;
    }
}
