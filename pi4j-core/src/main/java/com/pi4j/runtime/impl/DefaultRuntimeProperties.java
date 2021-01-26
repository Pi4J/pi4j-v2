package com.pi4j.runtime.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultRuntimeProperties.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <p>DefaultRuntimeProperties class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultRuntimeProperties implements RuntimeProperties {

    /** Constant <code>PI4J_PROPERTIES_FILE_NAME="pi4j.properties"</code> */
    public static String PI4J_PROPERTIES_FILE_NAME = "pi4j.properties";

    protected Map<String,String> properties = Collections.synchronizedMap(new HashMap<>());
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // static singleton instance
    /**
     * <p>newInstance.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @return a {@link com.pi4j.runtime.RuntimeProperties} object.
     */
    public static RuntimeProperties newInstance(Context context){
        return new DefaultRuntimeProperties(context);
    }

    private DefaultRuntimeProperties(Context context){
        // now lets load optional Pi4J.properties files from the file system

        // first; load any default Pi4J properties defined in the Environment Variables
        try{
            // iterate the environment variables looking for prefixes matching "PI4J_"
            var envars = System.getenv();
            envars.keySet().stream().filter(key -> key.toUpperCase().startsWith("PI4J_")).forEach((key)->{
                // sanitize keys by making them all lower case and replacing underscores with "." periods.
                String k = key.substring(5).replace('_', '.').toLowerCase();
                String v = envars.get(key);
                put(k, v);
            });

            // use "pi4j." prefix filter to limit the environment variables that we care about
            put(System.getenv(), "pi4j.");
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        // second; load system-scoped Pi4J properties file
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

        // third; load user-scoped Pi4J properties file
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

        // fourth; load an application-scoped Pi4J properties file
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

        // fifth; load any application-embedded resource Pi4J properties file
        // {app}/{resources}/pi4j.properties
        try {
            URL resource = getClass().getClassLoader().getResource(PI4J_PROPERTIES_FILE_NAME);
            if(resource != null) {
                File resourceFile = new File(resource.getFile());
                if (resourceFile != null && resourceFile.exists()) {
                    Properties p = new Properties();
                    p.load(new FileInputStream(resourceFile));
                    put(p);
                }
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

    /**
     * <p>sanitizeKey.</p>
     *
     * @param key a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    protected String sanitizeKey(String key){
        return key.trim().toLowerCase();
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(String key) {
        return properties.containsKey(sanitizeKey(key));
    }

    /** {@inheritDoc} */
    @Override
    public String get(String key) {
        String k = sanitizeKey(key);

        // first, attempt to get property for internal cache
        if(properties.containsKey(k)){
            return properties.get(k);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void put(String key, String value) {
        properties.put(sanitizeKey(key), value);
    }

    /** {@inheritDoc} */
    @Override
    public void put(Properties properties) {
        properties.forEach((key,value)->{
            this.properties.put(sanitizeKey(key.toString()), value.toString());
        });
    }

    /** {@inheritDoc} */
    @Override
    public void put(Map<String, String> values) {
        values.forEach((key,value)->{
            this.properties.put(sanitizeKey(key), value);
        });
    }

    /** {@inheritDoc} */
    @Override
    public void put(Map.Entry<String, String>... value) {
        for(Map.Entry e : value){
            this.properties.put(sanitizeKey(e.getKey().toString()), e.getValue().toString());
        }
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, String> all() {
        return Collections.unmodifiableMap(this.properties);
    }

    /** {@inheritDoc} */
    @Override
    public int count() {
        return this.properties.size();
    }


    /** {@inheritDoc} */
    protected void put(Properties properties, String prefixFilter){
        // convert java.util.Properties to a Map<String,String> object
        Map<String, String> entries = properties.keySet().stream()
                .collect(Collectors.toMap(k->k.toString(), key->properties.get(key).toString()));
        put(entries, prefixFilter);
    }

    /** {@inheritDoc} */
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
