package com.pi4j.util;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  ReflectionUtil.java
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>ReflectionUtil class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class ReflectionUtil {

    /**
     * <p>getAllInterfaces.</p>
     *
     * @param target a {@link java.lang.Object} object.
     * @return a {@link java.util.Collection} object.
     */
    public static Collection<Class> getAllInterfaces(Object target){
        Set<Class> results = new HashSet<>();
        return getAllInterfaces(target.getClass());
    }

    /**
     * <p>getAllInterfaces.</p>
     *
     * @param targetClass a {@link java.lang.Class} object.
     * @return a {@link java.util.Collection} object.
     */
    public static Collection<Class> getAllInterfaces(Class targetClass){
        Set<Class> results = new HashSet<>();

        // get all direct interfaces and their parents
        for(Class ifc : targetClass.getInterfaces()){
            results.add(ifc);
            results.addAll(getAllInterfaces(ifc));
        }

        // get all super classes and their interfaces
        if(targetClass.getSuperclass() != null){
            results.addAll(getAllInterfaces(targetClass.getSuperclass()));
        }

        // get all direct interfaces and their parents
        for(Class ifc : targetClass.getInterfaces()){
            if(ifc.getSuperclass() != null){
                results.addAll(getAllInterfaces(ifc.getSuperclass()));
            }
        }
        return results;
    }

    /**
     * <p>getAllClasses.</p>
     *
     * @param target a {@link java.lang.Object} object.
     * @return a {@link java.util.Collection} object.
     */
    public static Collection<Class> getAllClasses(Object target){
        Set<Class> results = new HashSet<>();
        return getAllClasses(target.getClass());
    }

    /**
     * <p>getAllClasses.</p>
     *
     * @param targetClass a {@link java.lang.Class} object.
     * @return a {@link java.util.Collection} object.
     */
    public static Collection<Class> getAllClasses(Class targetClass){
        Set<Class> results = new HashSet<>();

        results.add(targetClass);

        // get all super classes and their interfaces
        if(targetClass.getSuperclass() != null){
            results.add(targetClass.getSuperclass());
            results.addAll(getAllClasses(targetClass.getSuperclass()));
        }

        return results;
    }

}
