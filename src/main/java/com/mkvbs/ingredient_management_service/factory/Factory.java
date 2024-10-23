package com.mkvbs.ingredient_management_service.factory;

/**
 *
 * @param <C> defines what type of object will be created
 * @param <R> defines resource from which object will be created
 */
public interface Factory<C, R> {

    C create(R resource);
}
