/*
 * Copyright (c) Power Up Software Engineering LLC 2026.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.powerupsoftwareengineering.datastore.common.database.service;

import com.powerupsoftwareengineering.java.immutable.model.ModelObjectAbs;

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