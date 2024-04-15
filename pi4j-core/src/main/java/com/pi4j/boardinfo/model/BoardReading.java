package com.pi4j.boardinfo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardReading {

    private static final Logger logger = LoggerFactory.getLogger(BoardReading.class);

    private final String boardCode;
    private final String boardVersionCode;
    private final String temperature;
    private final String uptimeInfo;
    private final String volt;
    private final String memory;

    public BoardReading(String boardCode, String boardVersionCode, String temperature, String uptimeInfo,
                        String volt, String memory) {
       this.boardCode = boardCode;
       this.boardVersionCode = boardVersionCode;
       this.temperature = temperature;
       this.uptimeInfo = uptimeInfo;
       this.volt = volt;
       this.memory = memory;
    }

    public String getBoardCode() {
        return boardCode;
    }

    public String getBoardVersionCode() {
        return boardVersionCode;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getUptimeInfo() {
        return uptimeInfo;
    }

    public String getVolt() {
        return volt;
    }

    public String getMemory() {
        return memory;
    }

    public double getTemperatureInCelsius() {
        if (temperature.contains("temp=")) {
            try {
                return Double.parseDouble(temperature
                    .replace("temp=", "")
                    .replace("'C", "")
                    .replace("°C", ""));
            } catch (Exception e) {
                logger.error("Can't convert temperature value: {}", e.getMessage());
            }
        }
        return 0;
    }

    public double getTemperatureInFahrenheit() {
        return (getTemperatureInCelsius() * 1.8) + 32;
    }

    public double getVoltValue() {
        if (volt.contains("volt=")) {
            try {
                return Double.parseDouble(volt
                    .replace("volt=", "")
                    .replace("V", ""));
            } catch (Exception e) {
                logger.error("Can't convert volt value: {}", e.getMessage());
            }
        }
        return 0;
    }
}
