package com.pi4j.io.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  SerialConfigBuilder.java
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

import com.pi4j.config.DeviceConfigBuilder;
import com.pi4j.config.impl.DeviceConfigBuilderBase;
import com.pi4j.io.gpio.GpioConfigBuilder;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmPreset;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.io.pwm.impl.DefaultPwmConfigBuilder;
import com.pi4j.io.serial.impl.DefaultSerialConfigBuilder;

public interface SerialConfigBuilder extends DeviceConfigBuilder<SerialConfigBuilder, SerialConfig> {
    static SerialConfigBuilder newInstance()  {
        return DefaultSerialConfigBuilder.newInstance();
    }

    SerialConfigBuilder baud(Integer rate);
    default SerialConfigBuilder baud(Baud rate){
        return baud(rate.value());
    }

    SerialConfigBuilder dataBits(DataBits bits);
    default SerialConfigBuilder dataBits(Integer bits){
        return dataBits(DataBits.getInstance(bits));
    }

    SerialConfigBuilder parity(Parity parity);
    default SerialConfigBuilder parity(int parity){
        return parity(Parity.getInstance(parity));
    }

    SerialConfigBuilder stopBits(StopBits bits);
    default SerialConfigBuilder stopBits(Integer bits){
        return stopBits(StopBits.getInstance(bits));
    }

    SerialConfigBuilder flowControl(FlowControl control);
    default SerialConfigBuilder flowControl(Integer control){
        return flowControl(FlowControl.getInstance(control));
    }

    default SerialConfigBuilder baud_50()     { return baud(Baud._50);     }
    default SerialConfigBuilder baud_75()     { return baud(Baud._75);     }
    default SerialConfigBuilder baud_110()    { return baud(Baud._110);    }
    default SerialConfigBuilder baud_134()    { return baud(Baud._134);    }
    default SerialConfigBuilder baud_150()    { return baud(Baud._150);    }
    default SerialConfigBuilder baud_200()    { return baud(Baud._200);    }
    default SerialConfigBuilder baud_300()    { return baud(Baud._300);    }
    default SerialConfigBuilder baud_600()    { return baud(Baud._600);    }
    default SerialConfigBuilder baud_1200()   { return baud(Baud._1200);   }
    default SerialConfigBuilder baud_1800()   { return baud(Baud._1800);   }
    default SerialConfigBuilder baud_2400()   { return baud(Baud._2400);   }
    default SerialConfigBuilder baud_4800()   { return baud(Baud._4800);   }
    default SerialConfigBuilder baud_9600()   { return baud(Baud._9600);   }
    default SerialConfigBuilder baud_19200()  { return baud(Baud._19200);  }
    default SerialConfigBuilder baud_38400()  { return baud(Baud._38400);  }
    default SerialConfigBuilder baud_57600()  { return baud(Baud._57600);  }
    default SerialConfigBuilder baud_115200() { return baud(Baud._115200); }
    default SerialConfigBuilder baud_230400() { return baud(Baud._230400); }

    default SerialConfigBuilder dataBits_5()  { return dataBits(DataBits._5); }
    default SerialConfigBuilder dataBits_6()  { return dataBits(DataBits._6); }
    default SerialConfigBuilder dataBits_7()  { return dataBits(DataBits._7); }
    default SerialConfigBuilder dataBits_8()  { return dataBits(DataBits._8); }

    default SerialConfigBuilder parityNone()  { return parity(Parity.NONE);  }
    default SerialConfigBuilder parityOdd()   { return parity(Parity.ODD);   }
    default SerialConfigBuilder parityEven()  { return parity(Parity.EVEN);  }
    default SerialConfigBuilder parityMark()  { return parity(Parity.MARK);  }
    default SerialConfigBuilder paritySpace() { return parity(Parity.SPACE); }

    default SerialConfigBuilder flowNone()    { return flowControl(FlowControl.NONE);     }
    default SerialConfigBuilder flowHardware(){ return flowControl(FlowControl.HARDWARE); }
    default SerialConfigBuilder flowSoftware(){ return flowControl(FlowControl.SOFTWARE); }

    default SerialConfigBuilder stopBits_1()  { return stopBits(StopBits._1); }
    default SerialConfigBuilder stopBits_2()  { return stopBits(StopBits._2); }

    default SerialConfigBuilder baud8N1(Baud baud)  {
        return baud8N1(baud.value());
    }
    default SerialConfigBuilder baud8N1(Integer baud)  {
        baud(baud);
        dataBits(DataBits._8);
        parity(Parity.NONE);
        stopBits(StopBits._1);
        flowControl(FlowControl.NONE);
        return this;
    }

    default SerialConfigBuilder use_9600_N81()  {
        return baud8N1(Baud._9600);
    }
    default SerialConfigBuilder use_38400_N81()  {
        return baud8N1(Baud._38400);
    }
    default SerialConfigBuilder use_57600_N81()  {
        return baud8N1(Baud._57600);
    }
    default SerialConfigBuilder use_115200_N81()  {
        return baud8N1(Baud._115200);
    }
}
