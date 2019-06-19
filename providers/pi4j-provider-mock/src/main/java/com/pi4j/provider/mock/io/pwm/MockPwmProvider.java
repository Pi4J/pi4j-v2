package com.pi4j.provider.mock.io.pwm;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PROVIDER :: Mock Provider
 * FILENAME      :  MockPwmProvider.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.provider.mock.Mock;

import java.util.Collection;
import java.util.Map;

public class MockPwmProvider implements PwmProvider {

    public static final String NAME = Mock.PWM_PROVIDER_NAME;
    public static final String ID = Mock.PWM_PROVIDER_ID;

    @Override
    public String name() { return NAME; }

    @Override
    public String id() { return ID; }

    @Override
    public void initialize(Context context) throws Exception {

    }

    @Override
    public void terminate(Context context) throws Exception {

    }

    @Override
    public Pwm instance(PwmConfig config) throws Exception {
        return new MockPwm(config);
    }

    @Override
    public Collection<Pwm> instances() {
        return null;
    }
}
