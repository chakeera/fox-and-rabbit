package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;


public class Fox extends Animal {

    private int foodLevel;

    @Override
    public void init(boolean randomAge, Field field, Location location) {
        super.init(randomAge, field, location);
        foodLevel = RANDOM.nextInt(AnimalType.RABBIT.getFoodValue());
    }

    @Override
    protected int getMaxAge() {
        return 150;
    }

    @Override
    protected double getBreedingProbability() {
        return AnimalType.FOX.getBreedingProbability();
    }

    @Override
    protected int getMaxLiterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 15;
    }

    @Override
    public Location moveToNewLocation() {
        Location newLocation = findFood();
        if (newLocation == null) {
            newLocation = field.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newAnimals A list to return newly born foxes
     */
    @Override
    public void act(List<Methods> newAnimals) {
        incrementHunger();
        super.act(newAnimals);
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    public void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */

    public Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = AnimalType.RABBIT.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }
}
