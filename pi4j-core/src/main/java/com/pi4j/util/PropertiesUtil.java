package com.pi4j.util;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  PropertiesUtil.java
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PropertiesUtil {

//    public static Map<String,String> subKeys(Properties properties, String prefix){
//        final Map<String,String> result = new HashMap<>();
//
//        // if a filter was not provided, then load properties without a filter
//        if(StringUtil.isNullOrEmpty(prefix)) return result;
//
//        // normalize prefix
//        final String pfx = (prefix.endsWith(".")) ? prefix : prefix + ".";
//
//        // iterate the properties object and assign any key with the prefix filter to this config
//        properties.keySet().stream().filter(key -> key.toString().startsWith(pfx)).forEach((key)->{
//            result.put(key.toString().substring(prefix.length()+1), properties.get(key).toString());
//        });
//        return result;
//    }

    public static Map<String,String> subProperties(Map<String,String> properties, String prefix){
        // if a filter was not provided, then load properties without a filter
        if(StringUtil.isNullOrEmpty(prefix)) return Collections.emptyMap();

        // normalize prefix
        final String pfx = (prefix.endsWith(".")) ? prefix : prefix + ".";
        final Map<String,String> result = new HashMap<>();

        // iterate the properties object and assign any key with the prefix filter to this config
        properties.keySet().stream().filter(key -> key.startsWith(pfx)).forEach((key)->{
            result.put(key.substring(prefix.length()+1), properties.get(key));
        });
        return result;
    }

    public static Map<String,String> keysEndsWith(Map<String,String> properties, String suffix){
        // if a filter was not provided, then load properties without a filter
        if(StringUtil.isNullOrEmpty(suffix)) return Collections.emptyMap();

        // normalize suffix
        final String sfx = (suffix.startsWith(".")) ? suffix : "." + suffix;
        final Map<String,String> result = new HashMap<>();

        // iterate the properties object and assign any key with the prefix filter to this config
        properties.keySet().stream().filter(key -> key.endsWith(sfx)).forEach((key)->{
            result.put(key.substring(0, key.length() - suffix.length() - 1), properties.get(key));
        });
        return result;
    }
}
