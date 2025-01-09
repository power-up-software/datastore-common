package com.powerup.datastore.common.database.service;

import com.powerup.java.immutable.model.ModelObjectAbs;

/**
 * The <code>ModelObjectParameterGroup</code> class is a grouping of parameters related to a model object that are needed to perform database related
 * operations.
 *
 * @param <T> The type of model object the group is for
 * @param modelObject The model object the database operation is being performed on.
 * @param modelObjectClass The class of the model object.
 * @param builder The builder used to create a new version of the model object.
 *
 * @author Chris Picard
 */
public record ModelObjectParameterGroup<T extends ModelObjectAbs>(
        T modelObject,
        Class<T> modelObjectClass,
        ModelObjectAbs.Builder<T> builder) {
}