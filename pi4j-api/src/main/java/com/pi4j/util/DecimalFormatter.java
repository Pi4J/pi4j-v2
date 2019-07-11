package com.pi4j.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DecimalFormatter {

    protected static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
    protected static DecimalFormat df = new DecimalFormat("#.##################", otherSymbols);

    public static String format(Number value){
        return df.format(value);
    }
}
