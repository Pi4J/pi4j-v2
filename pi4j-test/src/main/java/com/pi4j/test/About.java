package com.pi4j.test;/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  About.java
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
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.IOType;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;

public class About {


    public About() throws Exception {
    }

    public void enumerateBindings(Context context) throws Pi4JException {
        enumerateBindings(context,"BINDINGS");
    }
    public void enumerateBindings(Context context, String title) throws Pi4JException {
        System.out.println("=====================================================");
        System.out.println(title);
        System.out.println("=====================================================");
        for (Binding binding : context.bindings().all().values()) {
            System.out.println("  " + binding.name() + " [" + binding.id() + "]; ");
        }
    }

    public void enumerateProviders(Context context) throws Pi4JException {
        enumerateProviders(context,"PROVIDERS");
    }
    public void enumerateProviders(Context context, String title) throws Pi4JException {
        System.out.println("=====================================================");
        System.out.println(title);
        System.out.println("=====================================================");
        for (Provider provider : context.providers().all().values()) {
            System.out.println("  " + provider.name() + " [" + provider.id() + "]; " + provider.type());
        }
    }

    public void enumerateProviders(Context context, IOType ioType) throws Pi4JException {
        System.out.println("=====================================================");
        System.out.println(ioType + " PROVIDERS");
        System.out.println("=====================================================");
        for(var provider : context.providers().all(ioType).values()){
            System.out.println("  " + provider.name() + " [" + provider.id() + "]; " + provider.type());
        }
    }

    public void enumeratePlatforms(Context context) throws Pi4JException {
        System.out.println("=====================================================");
        System.out.println("PLATFORMS");
        System.out.println("=====================================================");
        for (Platform platform : context.platforms().all().values()) {
            System.out.println("  " + platform.name() + " [" + platform.id() + "]; " + platform.getDescription());
        }
    }

    public void describeDeafultPlatform(Context context) throws Pi4JException {
        System.out.println("=====================================================");
        System.out.println("DEFAULT (RUNTIME) PLATFORM ");
        System.out.println("=====================================================");
        System.out.println("  " + context.platform().name() + " [" + context.platform().id() + "]");
        context.platform().describe().print(System.out);
    }

//    public void enumeratePlatformProviders() throws Pi4JException {
//        System.out.println("=====================================================");
//        System.out.println("PLATFORM PROVIDERS");
//        System.out.println("=====================================================");
//        for(var provider : Pi4J.platform().providers().values()){
//            System.out.println("  " + provider.name() + "[" + provider.id() + "]; " + provider.type());
//        }
//    }
}
