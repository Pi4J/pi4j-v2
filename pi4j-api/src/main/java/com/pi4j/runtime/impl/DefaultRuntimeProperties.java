package com.pi4j.runtime.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultRuntimeProperties.java
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

import com.pi4j.context.Context;
import com.pi4j.runtime.RuntimeProperties;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class DefaultRuntimeProperties implements RuntimeProperties {

    public static String PI4J_PROPERTIES_FILE_NAME = "pi4j.properties";

    protected Map<String,String> properties = Collections.synchronizedMap(new HashMap<>());
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // static singleton instance
    public static RuntimeProperties newInstance(Context context){
        return new DefaultRuntimeProperties(context);
    }

    private DefaultRuntimeProperties(Context context){
        // now lets load optional Pi4J.properties files from the file system

        // first; load any default Pi4J properties defined in the Environment Variables
        try{
            // use "pi4j." prefix filter to limit the environment variables that we care about
            put(System.getenv(), "pi4j.");
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        // first; load system-scoped Pi4J properties file
        // /etc/pi4j/pi4j.properties
        try {
            Path appFile = Paths.get("/etc/pi4j", PI4J_PROPERTIES_FILE_NAME);
            if (Files.exists(appFile)) {
                Properties p = new Properties();
                p.load(new FileInputStream(appFile.toFile()));
                put(p);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        // second; load user-scoped Pi4J properties file
        // ~/.pi4j.properties
        try {
            Path appFile = Paths.get(System.getProperty("user.home"), "." + PI4J_PROPERTIES_FILE_NAME);
            if (Files.exists(appFile)) {
                Properties p = new Properties();
                p.load(new FileInputStream(appFile.toFile()));
                put(p);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        // third; load an application-scoped Pi4J properties file
        // {pwd}/pi4j.properties
        try {
            Path appFile = Paths.get(System.getProperty("user.dir"), PI4J_PROPERTIES_FILE_NAME);
            if (Files.exists(appFile)) {
                Properties p = new Properties();
                p.load(new FileInputStream(appFile.toFile()));
                put(p);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        // fourth; load an application-embedded resource Pi4J properties file
        // {app}/{resources}/pi4j.properties
        try {
            File resourceFile = new File(getClass().getClassLoader().getResource(PI4J_PROPERTIES_FILE_NAME).getFile());
            if(resourceFile.exists()) {
                Properties p = new Properties();
                p.load(new FileInputStream(resourceFile));
                put(p);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        // finally; load any default properties defined in the System.properties
        try{
            // use "pi4j." prefix filter to limit the system properties that we care about
            put(System.getProperties(), "pi4j.");
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }


        // add all context pre-configured properties to the runtime properties cache
        this.put(context.config().properties());
    }

    protected String sanitizeKey(String key){
        return key.trim().toLowerCase();
    }

    @Override
    public boolean has(String key) {
        return properties.containsKey(sanitizeKey(key));
    }

    @Override
    public String get(String key) {
        String k = sanitizeKey(key);

        // first, attempt to get property for internal cache
        if(properties.containsKey(k)){
            return properties.get(k);
        }
        return null;
    }

    @Override
    public void put(String key, String value) {
        properties.put(sanitizeKey(key), value);
    }

    @Override
    public void put(Properties properties) {
        properties.forEach((key,value)->{
            this.properties.put(sanitizeKey(key.toString()), value.toString());
        });
    }

    @Override
    public void put(Map<String, String> values) {
        values.forEach((key,value)->{
            this.properties.put(sanitizeKey(key), value);
        });
    }

    @Override
    public void put(Map.Entry<String, String>... value) {
        for(Map.Entry e : value){
            this.properties.put(sanitizeKey(e.getKey().toString()), e.getValue().toString());
        }
    }

    @Override
    public Map<String, String> all() {
        return Collections.unmodifiableMap(this.properties);
    }

    @Override
    public int count() {
        return this.properties.size();
    }


    protected void put(Properties properties, String prefixFilter){
        // convert java.util.Properties to a Map<String,String> object
        Map<String, String> entries = properties.keySet().stream()
                .collect(Collectors.toMap(k->k.toString(), key->properties.get(key).toString()));
        put(entries, prefixFilter);
    }

    protected void put(Map<String,String> properties, String prefixFilter){

        // if a filter was not provided, then load properties without a filter
        if(StringUtil.isNullOrEmpty(prefixFilter)) {
            put(properties);
            return;
        }

        // sanitize the prefix filter and make sure it includes a "." character at the end
        var prefix = (prefixFilter.endsWith(".")) ? prefixFilter : prefixFilter+".";

        // iterate the properties object and assign any key with the prefix filter to this config
        properties.keySet().stream().filter(key -> key.startsWith(prefix)).forEach((key)->{
            put(key.substring(prefix.length()), properties.get(key));
        });
    }
}
