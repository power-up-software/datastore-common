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

import com.powerupsoftwareengineering.datastore.common.api.exception.DatastoreSaveException;
import com.powerupsoftwareengineering.java.immutable.model.ModelObject;
import org.apache.ibatis.session.SqlSession;

/**
 * The <code>UpdateFunction</code> class defines a lambda expression function for a database update operation.
 *
 * @param <OneT> The type of model object being updated
 * @param <TwoT> The sql session to use to perform the update operation
 *
 * @author Chris Picard
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface UpdateFunction<OneT extends ModelObject, TwoT extends SqlSession> {

    /**
     * Apply invokes the function this lambda function represents.
     *
     * @param one The updated model object
     * @param two The existing model object
     * @param three The sql session to use to perform the update operation
     *
     * @throws DatastoreSaveException Unable to update model object
     */
    @SuppressWarnings("RedundantThrows")
    void apply(OneT one, OneT two, TwoT three) throws DatastoreSaveException;
}