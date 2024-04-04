package com.pi4j.boardinfo.definition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pi4j.boardinfo.model.HeaderPin;

import java.util.ArrayList;
import java.util.List;

/**
 * List of pins in a Raspberry Pi header.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HeaderPins {
    HEADER_8("8pin header", get8PinsHeader()),
    HEADER_26_TYPE_1("26pin header - type 1", get26PinsHeader(1)),
    HEADER_26_TYPE_2("26pin header - type 2", get26PinsHeader(2)),
    HEADER_40("40pin header", get40PinsHeader()),
    COMPUTE_J5("Compute J5", getComputeJ5()),
    COMPUTE_J6("Compute J6", getComputeJ6());

    private final String label;
    private List<HeaderPin> pins;

    HeaderPins(String label, List<HeaderPin> pins) {
        this.label = label;
        this.pins = pins;
    }

    public String getLabel() {
        return label;
    }

    public List<HeaderPin> getPins() {
        return pins;
    }

    static List<HeaderPin> get8PinsHeader() {
        List<HeaderPin> header = new ArrayList<>();

        header.add(new HeaderPin(1, PinType.POWER,  "5.0 VDC"));
        header.add(new HeaderPin(2, PinType.POWER,  "3.3 VDC"));
        header.add(new HeaderPin(3, PinType.DIGITAL, null, 28, 17, ""));
        header.add(new HeaderPin(4, PinType.DIGITAL, null, 29, 18, ""));
        header.add(new HeaderPin(5, PinType.DIGITAL, null, 30, 19, ""));
        header.add(new HeaderPin(6, PinType.DIGITAL, null, 31, 20, ""));
        header.add(new HeaderPin(7, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(8, PinType.GROUND,   "Ground"));

        return header;
    }

    static List<HeaderPin> get26PinsHeader(int type) {
        List<HeaderPin> header = new ArrayList<>();

        header.add(new HeaderPin(1, PinType.POWER,  "3.3 VDC"));
        header.add(new HeaderPin(2, PinType.POWER,  "5.0 VDC"));
        header.add(new HeaderPin(3, PinType.DIGITAL_NO_PULL_DOWN, PinFunction.I2C, (type == 1 ? 0 : 2), 8, "SDA1 (I2C)", "SDA.1 pin has a physical pull-up resistor"));
        header.add(new HeaderPin(4, PinType.POWER,  "5.0 VDC"));
        header.add(new HeaderPin(5, PinType.DIGITAL_NO_PULL_DOWN, PinFunction.I2C, (type == 1 ? 1 : 3), 9, "SCL1 (I2C)", "SCL.1 pin has a physical pull-up resistor"));
        header.add(new HeaderPin(6, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(7, PinType.DIGITAL, PinFunction.GPCLK, 4, 7, "GPCLK0"));
        header.add(new HeaderPin(8, PinType.DIGITAL, PinFunction.UART, 14, 15,  "UART TxD"));
        header.add(new HeaderPin(9, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(10, PinType.DIGITAL, PinFunction.UART, 15, 16, "UART RxD"));
        header.add(new HeaderPin(11, PinType.DIGITAL, PinFunction.SPI, 17, 0, ""));
        header.add(new HeaderPin(12, PinType.DIGITAL_AND_PWM, PinFunction.SPI, 18, 1, "PCM_CLK/PWM0", "Supports PWM0 [ALT5]"));
        header.add(new HeaderPin(13, PinType.DIGITAL, null, (type == 1 ? 21 : 27), 2, ""));
        header.add(new HeaderPin(14, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(15, PinType.DIGITAL, null, 22, 3, ""));
        header.add(new HeaderPin(16, PinType.DIGITAL, null, 23, 4, ""));
        header.add(new HeaderPin(17, PinType.POWER,  "3.3 VDC"));
        header.add(new HeaderPin(18, PinType.DIGITAL, null, 24, 5, ""));
        header.add(new HeaderPin(19, PinType.DIGITAL, PinFunction.SPI, 10, 12, "MOSI (SPI)"));
        header.add(new HeaderPin(20, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(21, PinType.DIGITAL, PinFunction.SPI, 9, 13, "MISO (SPI)"));
        header.add(new HeaderPin(22, PinType.DIGITAL, null, 25, 6, ""));
        header.add(new HeaderPin(23, PinType.DIGITAL, PinFunction.SPI, 11, 14, "SCLK (SPI)"));
        header.add(new HeaderPin(24, PinType.DIGITAL, PinFunction.SPI, 8, 10, "CE0 (SPI)"));
        header.add(new HeaderPin(25, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(26, PinType.DIGITAL, PinFunction.SPI, 7, 11, "CE1 (SPI)"));

        return header;
    }

    static List<HeaderPin> get40PinsHeader() {
        List<HeaderPin> header = new ArrayList<>();

        header.addAll(get26PinsHeader(2));

        header.add(new HeaderPin(27, PinType.DIGITAL_NO_PULL_DOWN, PinFunction.I2C, 0, 30, "SDA0 I2C ID EEPROM", "SDA.0 pin has a physical pull-up resistor"));
        header.add(new HeaderPin(28, PinType.DIGITAL_NO_PULL_DOWN, PinFunction.I2C, 1, 31, "SCL0 I2C ID EEPROM", "SDC.0 pin has a physical pull-up resistor"));
        header.add(new HeaderPin(29, PinType.DIGITAL, PinFunction.GPCLK, 5, 21, "GPCLK1"));
        header.add(new HeaderPin(30, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(31, PinType.DIGITAL, PinFunction.GPCLK, 6, 22, "GPCL2"));
        header.add(new HeaderPin(32, PinType.DIGITAL_AND_PWM, null, 12, 26, "PWM0", "Supports PWM0 [ALT0]"));
        header.add(new HeaderPin(33, PinType.DIGITAL_AND_PWM, null, 13, 23, "PWM1", "Supports PWM1 [ALT0]"));
        header.add(new HeaderPin(34, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(35, PinType.DIGITAL_AND_PWM, PinFunction.SPI, 19,24, "PCM_FS/PWM1", "Supports PWM1 [ALT5]"));
        header.add(new HeaderPin(36, PinType.DIGITAL, PinFunction.SPI, 16, 27, ""));
        header.add(new HeaderPin(37, PinType.DIGITAL, null, 26, 25, ""));
        header.add(new HeaderPin(38, PinType.DIGITAL, PinFunction.SPI, 20, 28, "PCM_DIN"));
        header.add(new HeaderPin(39, PinType.GROUND,  "Ground"));
        header.add(new HeaderPin(40, PinType.DIGITAL, PinFunction.SPI, 21, 29, "PCM_DOUT"));

        return header;
    }

    static List<HeaderPin> getComputeJ5() {
        List<HeaderPin> header = new ArrayList<>();

        // TODO
        // https://pi4j.com/1.2/pins/model-cm-rev1.html#J5_Pinout_60-pin_Header

        return header;
    }

    static List<HeaderPin> getComputeJ6() {
        List<HeaderPin> header = new ArrayList<>();

        // TODO
        // https://pi4j.com/1.2/pins/model-cm-rev1.html#J6_Pinout_60-pin_Header

        return header;
    }
}
