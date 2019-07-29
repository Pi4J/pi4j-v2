package com.pi4j.io.pwm;

import com.pi4j.context.Context;
import com.pi4j.provider.ProviderBase;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  PwmProviderBase.java
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
public abstract class PwmProviderBase
        extends ProviderBase<PwmProvider, Pwm, PwmConfig>
        implements PwmProvider {

    public PwmProviderBase(){
        super();
    }

    public PwmProviderBase(String id){
        super(id);
    }

    public PwmProviderBase(String id, String name){
        super(id, name);
    }

    @Override
    public abstract Pwm create(Context context, PwmConfig config) throws Exception;
}
