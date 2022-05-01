package com.pi4j.plugin.linuxfs.provider.pwm;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: LinuxFS I/O Providers
 * FILENAME      :  LinuxFsPwmProviderImpl.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * 
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

import com.pi4j.io.exception.IOException;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmProviderBase;
import com.pi4j.io.pwm.PwmType;

/**
 * <p>LinuxFsPwmProviderImpl class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class LinuxFsPwmProviderImpl extends PwmProviderBase implements LinuxFsPwmProvider {

    final String pwmFileSystemPath;
    final int pwmChip;

    /**
     * <p>Constructor for LinuxFsPwmProviderImpl.</p>
     */
    public LinuxFsPwmProviderImpl(String pwmFileSystemPath, int pwmChip) {
        this.id = ID;
        this.name = NAME;
        this.pwmFileSystemPath = pwmFileSystemPath;
        this.pwmChip = pwmChip;
    }

    /**
     * <p>Constructor for LinuxFsPwmProviderImpl.</p>
     */
    public LinuxFsPwmProviderImpl(String pwmFileSystemPath) {
        this.id = ID;
        this.name = NAME;
        this.pwmFileSystemPath = pwmFileSystemPath;
        this.pwmChip = LinuxPwm.DEFAULT_PWM_CHIP;
    }

    /** {@inheritDoc} */
    @Override
    public Pwm create(PwmConfig config) {

        // create new I/O instance based on I/O config
        if(config.pwmType() == PwmType.HARDWARE){

            // create filesystem based PWM instance using instance address (PWM PIN NUMBER)
            LinuxPwm pwm = new LinuxPwm(this.pwmFileSystemPath, this.pwmChip, config.address());
            return new LinuxFsPwm(pwm, this, config);
        }
        else {
            throw new IOException("The Linux file system PWM provider does not support software-emulated PWM pins; PIN ADDRESS=" + config.address());
        }
    }
}
