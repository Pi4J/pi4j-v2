package com.pi4j.common;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  Metadata.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
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


import com.pi4j.common.impl.MetadataImpl;

import java.util.Collection;

/**
 * <p>Metadata interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Metadata  {

//    static Metadata create(){
//        return new MetadataImpl();
//    }
//
//    static Metadata create(String key){
//        return create().key(key);
//    }
//
//    static Metadata create(String key, Object value){
//        return create(key).value(value);
//    }
//
//    static Metadata create(String key, Object value, String description){
//        return create(key, value).description(description);
//    }

    /**
     * <p>size.</p>
     *
     * @return a int.
     */
    int size();
    /**
     * <p>isEmpty.</p>
     *
     * @return a boolean.
     */
    boolean isEmpty();
    /**
     * <p>contains.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean contains(String key);
    /**
     * <p>remove.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Metadatum} object.
     */
    Metadatum remove(String key);

    /**
     * <p>put.</p>
     *
     * @param metadatum a {@link com.pi4j.common.Metadatum} object.
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    Metadata put(Metadatum metadatum);
    /**
     * <p>clear.</p>
     *
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    Metadata clear();
    /**
     * <p>get.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Metadatum} object.
     */
    Metadatum get(String key);
    /**
     * <p>all.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    Collection<Metadatum> all();

    /**
     * <p>put.</p>
     *
     * @param c a {@link java.util.Collection} object.
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    default Metadata put(Collection<? extends Metadatum> c){
        c.forEach((m)->{
            put(m);
        });
        return this;
    }

    /**
     * <p>put.</p>
     *
     * @param metadata a {@link com.pi4j.common.Metadata} object.
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    default Metadata put(Metadata metadata){
        metadata.all().forEach((m)->{
            put(m);
        });
        return this;
    }

    /**
     * <p>contains.</p>
     *
     * @param metadatum a {@link com.pi4j.common.Metadatum} object.
     * @return a boolean.
     */
    default boolean contains(Metadatum metadatum){
        return contains(metadatum.key());
    }

    /**
     * <p>getValue.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     */
    default Object getValue(String key){
        return get(key).value();
    }

    /**
     * <p>put.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    default Metadata put(String key){
        return put(Metadatum.create(key));
    }
    /**
     * <p>put.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    default Metadata put(String key, Object value){
        return put(Metadatum.create(key, value));
    }
    /**
     * <p>put.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     * @param description a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    default Metadata put(String key, Object value, String description){
        return put(Metadatum.create(key, value, description));
    }

    /**
     * <p>create.</p>
     *
     * @return a {@link com.pi4j.common.Metadata} object.
     */
    static Metadata create(){
        return new MetadataImpl();
    }
}
