package com.pi4j.config;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Config.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * <p>Config interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Config<CONFIG_TYPE> {
    /** Constant <code>ID_KEY="id"</code> */
    String ID_KEY = "id";
    /** Constant <code>NAME_KEY="name"</code> */
    String NAME_KEY = "name";
    /** Constant <code>DESCRIPTION_KEY="description"</code> */
    String DESCRIPTION_KEY = "description";

    /**
     * <p>id.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String id();

    /**
     * <p>name.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String name();
    /**
     * <p>name.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @return a CONFIG_TYPE object.
     */
    CONFIG_TYPE name(String name);

    /**
     * <p>description.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String description();
    /**
     * <p>description.</p>
     *
     * @param description a {@link java.lang.String} object.
     * @return a CONFIG_TYPE object.
     */
    CONFIG_TYPE description(String description);

    /**
     * <p>validate.</p>
     */
    void validate();

    /**
     * <p>getId.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    default String getId(){
        return this.id();
    }
    /**
     * <p>getName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    default String getName(){
        return this.name();
    }
    /**
     * <p>getDescription.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    default String getDescription(){
        return this.description();
    }
    /**
     * <p>setName.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    default void setName(String name){
        this.name(name);
    }
    /**
     * <p>setDescription.</p>
     *
     * @param description a {@link java.lang.String} object.
     */
    default void setDescription(String description){
        this.description(description);
    }
}
