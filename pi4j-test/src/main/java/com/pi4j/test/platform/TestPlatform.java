package com.pi4j.test.platform;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  TestPlatform.java
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
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformBase;
import com.pi4j.provider.Provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestPlatform extends PlatformBase<TestPlatform> implements Platform {

    protected Set<String> supportedProviderIds = new HashSet<String>();

    public TestPlatform(){ super(); }

    public TestPlatform(String id){
        super(id);
    }

    public TestPlatform(String id, String name){
        super(id, name);
    }

    public TestPlatform(String id, String name, String description){
        super(id, name, description);
    }

    @Override
    public int weight() {
        // the Test platform is weighted at zero to indicate that it has a very
        // low priority and should only be used in the case where other platforms
        // are not found in the classpath
        return 0;
    }

    @Override
    public boolean enabled(Context context) {
        // the Tet Platform is always available when detected
        // there are no logic checked required to determine when
        // and if the test platform should be enabled
        return true;
    }

    @Override
    protected String[] getProviders() {
        return this.supportedProviderIds.toArray(new String[]{});
    }

    public void setProviders(String ... providerId){
        this.supportedProviderIds.clear();
        this.supportedProviderIds.addAll(Arrays.asList(providerId));
    }

    public void setProviders(Provider ... provider){
        this.supportedProviderIds.clear();
        for(Provider p : provider) {
            this.supportedProviderIds.add(p.id());
        }
    }
}