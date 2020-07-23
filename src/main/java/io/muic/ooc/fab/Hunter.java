package io.muic.ooc.fab;

import io.muic.ooc.fab.Field;
import io.muic.ooc.fab.Location;

import java.util.Iterator;
import java.util.List;

public class Hunter extends Animal {

    /**
     * Create a Hunter. A Hunter can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    @Override
    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
    }

    @Override
    protected double getBreedingProbability() {
        return  AnimalType.HUNTER.getBreedingProbability();
    }

    @Override
    protected int getMaxLiterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 80;
    }

    @Override
    public Location moveToNewLocation() {
        Location newLocation = findFood();
        if (newLocation == null) {
            newLocation = field.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    // make it immortal;
    @Override
    protected int getMaxAge() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void act(List<Methods> newAnimals) {
        super.act(newAnimals);
    }

    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    return where;
                }
            }
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    return where;
                }
            }
            /*
             * Hunt a tiger also
             */
            if (animal instanceof Tiger) {
                Tiger tiger = (Tiger) animal;
                if (tiger.isAlive()) {
                    tiger.setDead();
                    return where;
                }
            }
        }
        return null;
    }
}