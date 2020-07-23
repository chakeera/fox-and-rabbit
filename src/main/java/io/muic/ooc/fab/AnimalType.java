package io.muic.ooc.fab;

import java.awt.*;


public enum AnimalType {

    RABBIT(0.1, Rabbit.class, Color.GREEN, 8),
    FOX(0.1, Fox.class, Color.PINK, 20),
    TIGER(0.03, Tiger.class, Color.RED, 40),
    HUNTER(0.0009, Hunter.class, Color.BLACK, 0);

    private double breedingProbability;

    private Class animalClass;

    private Color color;

    private int foodValue;

    AnimalType(double breedingProbability, Class animalClass, Color color, int foodValue) {
        this.breedingProbability = breedingProbability;
        this.animalClass = animalClass;
        this.color = color;
        // adding the food value for each animals for solving dependence problem //
        this.foodValue = foodValue;
    }

    public double getBreedingProbability() {
        return breedingProbability;
    }

    public Class getAnimalClass() {
        return animalClass;
    }

    public Color getColor() {
        return color;
    }

    public int getFoodValue() {
        return foodValue;
    }
}
