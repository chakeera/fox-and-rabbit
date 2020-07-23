package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal implements Methods {
    // Characteristics shared by all animals (class variables).

    // Random generator
    protected static final Random RANDOM = new Random();

    // Encapsulation
    // Whether the animal is alive or not.
    private boolean alive = true;

    // The animal's position.
    private Location location;

    // The field occupied.
    protected Field field;

    // Individual characteristics (instance fields).
    // The animal's age.
    private int age = 0;

    /*
     * These methods will be changed according to each species
     */
    public abstract Location moveToNewLocation();

    protected abstract int getBreedingAge();

    protected abstract int getMaxLiterSize();

    protected abstract double getBreedingProbability();

    protected abstract int getMaxAge();

    @Override
    public void initialize(boolean randomAge, Field field, Location location) {
        this.field = field;
        setLocation(location);
        if (randomAge) {
            age = RANDOM.nextInt(getMaxAge());
        }
    }

    @Override
    public void act(List<Methods> newAnimals) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);
            // Try to move into a free location.
            Location newLocation = moveToNewLocation();

            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    @Override
    public Location getLocation() {
        return location;
    }


    @Override
    public void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    @Override
    public void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    @Override
    public void giveBirth(List<Methods> newAnimals) {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            /*
            ? Need a factory for abstract class
             */
            Animal young = breedOne(field, loc);
            newAnimals.add(young);
        }
    }

    @Override
    public Animal breedOne(Field field, Location location) {
        return AnimalFactory.createAnimal(getClass(), field, location);
    }


    @Override
    public int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLiterSize()) + 1;
        }
        return births;
    }

    @Override
    public boolean canBreed() {
        return age >= getBreedingAge();
    }

}