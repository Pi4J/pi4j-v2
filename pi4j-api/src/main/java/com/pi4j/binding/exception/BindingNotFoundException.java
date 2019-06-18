package com.pi4j.binding.exception;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  BindingNotFoundException.java
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


import com.pi4j.binding.Binding;

/**
 * <p>
 * This exception is thrown if a platform assignment is attempted when a
 * platform instance has already been assigned.
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class BindingNotFoundException extends BindingException {

    /**
     * Default Constructor
     */
    public BindingNotFoundException(){
        super("Pi4J binding could not be detected.  Please include the 'binding' JAR in the classpath for your io.");
    }

    /**
     * Alternate Constructor
     */
    public BindingNotFoundException(String bindingId){
        super("Pi4J binding [" + bindingId + "] could not be found.  Please include the 'binding' JAR in the classpath for your io.");
    }

    /**
     * Alternate Constructor
     */
    public BindingNotFoundException(Class<? extends Binding> bindingClass){
        super("Pi4J binding for [" + bindingClass + "] could not be found.  Please include a 'binding' JAR in the classpath for your io for this binding type.");
    }

}
