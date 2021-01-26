package com.pi4j.io.binding.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  BindingBase.java
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

import com.pi4j.io.binding.Binding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class BindingBase<BINDING_TYPE extends Binding, MEMBER_TYPE> implements Binding<BINDING_TYPE, MEMBER_TYPE> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected boolean state = false;
    protected Set<MEMBER_TYPE> members = new CopyOnWriteArraySet<>();

    public BindingBase(MEMBER_TYPE ... member){
        add(member);
    }

    @Override
    public BINDING_TYPE add(MEMBER_TYPE... member) {
        members.addAll(List.of(member));
        return (BINDING_TYPE)this;
    }

    @Override
    public BINDING_TYPE remove(MEMBER_TYPE... member) {
        members.removeAll(List.of(members));
        return (BINDING_TYPE)this;
    }

    @Override
    public BINDING_TYPE removeAll() {
        members.clear();
        return (BINDING_TYPE)this;
    }

    @Override
    public Collection<MEMBER_TYPE> members() {
        return Collections.unmodifiableSet(members);
    }
}
