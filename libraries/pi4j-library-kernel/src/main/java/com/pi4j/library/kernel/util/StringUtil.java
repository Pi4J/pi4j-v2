package com.pi4j.library.kernel.util;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class StringUtil {

        /** Constant <code>EMPTY=""</code> */
        public static final String EMPTY = "";
        /** Constant <code>DEFAULT_PAD_CHAR=' '</code> */
        public static final char DEFAULT_PAD_CHAR = ' ';

        /**
         * <p>isNullOrEmpty.</p>
         *
         * @param data a {@link String} object.
         * @param trim a boolean.
         * @return a boolean.
         */
        public static boolean isNullOrEmpty(String data, boolean trim){
        if(data == null)
            return true;

        // trim if requested
        String test = data;
        if(trim)
            test = data.trim();

        return (test.length() <= 0);
    }

        /**
         * <p>isNullOrEmpty.</p>
         *
         * @param data a {@link String} object.
         * @return a boolean.
         */
        public static boolean isNullOrEmpty(String data){
        return isNullOrEmpty(data, false);
    }

        /**
         * <p>isNotNullOrEmpty.</p>
         *
         * @param data a {@link String} object.
         * @return a boolean.
         */
        public static boolean isNotNullOrEmpty(String data){
        return isNotNullOrEmpty(data, false);
    }

        /**
         * <p>isNotNullOrEmpty.</p>
         *
         * @param data a {@link String} object.
         * @param trim a boolean.
         * @return a boolean.
         */
        public static boolean isNotNullOrEmpty(String data, boolean trim){
        return !(isNullOrEmpty(data, trim));
    }

        /**
         * <p>setIfNullOrEmpty.</p>
         *
         * @param data a {@link String} object.
         * @param replacement a {@link String} object.
         * @param trim a boolean.
         * @return a {@link String} object.
         */
        public static String setIfNullOrEmpty(String data, String replacement, boolean trim){
        if(isNullOrEmpty(data, trim)) {
            return replacement;
        }
        return data;
    }

        /**
         * <p>setIfNullOrEmpty.</p>
         *
         * @param data a {@link String} object.
         * @param replacement a {@link String} object.
         * @return a {@link String} object.
         */
        public static String setIfNullOrEmpty(String data, String replacement){
        return setIfNullOrEmpty(data, replacement, false);
    }

        /**
         * <p>contains.</p>
         *
         * @param source a {@link String} object.
         * @param target a {@link String} object.
         * @return a boolean.
         */
        public static boolean contains(String source, String target)  {
        return (null != source && null != target && source.contains(target));
    }

        /**
         * <p>contains.</p>
         *
         * @param source a {@link String} object.
         * @param targets an array of {@link String} objects.
         * @return a boolean.
         */
        public static boolean contains(String source, String[] targets)  {
        if (null != source && null != targets) {
            for(var target : targets) {
                if (source.contains(target)) {
                    return true;
                }
            }
        }
        return false;
    }

        /**
         * <p>contains.</p>
         *
         * @param sources an array of {@link String} objects.
         * @param target a {@link String} object.
         * @return a boolean.
         */
        public static boolean contains(String[] sources, String target)  {
        if (null != sources && null != target) {
            for (var source : sources) {
                if(contains(source, target))
                    return true;
            }
        }
        return false;
    }

        /**
         * <p>contains.</p>
         *
         * @param sources an array of {@link String} objects.
         * @param targets an array of {@link String} objects.
         * @return a boolean.
         */
        public static boolean contains(String[] sources, String[] targets)  {
        if (null != sources && null != targets) {
            for (var source : sources) {
                if(contains(source, targets))
                    return true;
            }
        }
        return false;
    }

        /**
         * <p>create.</p>
         *
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String create(int length)  {
        return create(DEFAULT_PAD_CHAR, length);
    }

        /**
         * <p>create.</p>
         *
         * @param c a char.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String create(char c, int length)  {
        StringBuilder sb = new StringBuilder(length);
        for(var index = 0; index < length; index++)
            sb.append(c);
        return sb.toString();
    }

        /**
         * <p>create.</p>
         *
         * @param s a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String create(String s, int length)  {
        StringBuilder sb = new StringBuilder(length * s.length());
        for(var index = 0; index < length; index++)
            sb.append(s);
        return sb.toString();
    }

        /**
         * <p>repeat.</p>
         *
         * @param c a char.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String repeat(char c, int length)  {
        return create(c, length);
    }

        /**
         * <p>repeat.</p>
         *
         * @param s a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String repeat(String s, int length)  {
        return create(s, length);
    }

        /**
         * <p>padLeft.</p>
         *
         * @param data a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padLeft(String data, int length)  {
        return padLeft(data, DEFAULT_PAD_CHAR, length);
    }

        /**
         * <p>padLeft.</p>
         *
         * @param data a {@link String} object.
         * @param pad a char.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padLeft(String data, char pad, int length)  {
        var sb = new StringBuilder(data.length() + length);
        for(var index = 0; index < length; index++)
            sb.append(pad);
        sb.append(data);
        return sb.toString();
    }

        /**
         * <p>padLeft.</p>
         *
         * @param data a {@link String} object.
         * @param pad a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padLeft(String data, String pad, int length)  {
        var sb = new StringBuilder(data.length() + (length * pad.length()));
        for(var index = 0; index < length; index++)
            sb.append(pad);
        sb.append(data);
        return sb.toString();
    }

        /**
         * <p>padRight.</p>
         *
         * @param data a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padRight(String data, int length)  {
        return padRight(data, DEFAULT_PAD_CHAR, length);
    }

        /**
         * <p>padRight.</p>
         *
         * @param data a {@link String} object.
         * @param pad a char.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padRight(String data, char pad, int length)  {
        var sb = new StringBuilder(data.length() + length);
        sb.append(data);
        for(var index = 0; index < length; index++)
            sb.append(pad);
        return sb.toString();
    }

        /**
         * <p>padRight.</p>
         *
         * @param data a {@link String} object.
         * @param pad a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padRight(String data, String pad, int length)  {
        var sb = new StringBuilder(data.length() + (length * pad.length()));
        sb.append(data);
        for(var index = 0; index < length; index++)
            sb.append(pad);
        return sb.toString();
    }

        /**
         * <p>pad.</p>
         *
         * @param data a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String pad(String data, int length)  {
        return pad(data, DEFAULT_PAD_CHAR, length);
    }

        /**
         * <p>pad.</p>
         *
         * @param data a {@link String} object.
         * @param pad a char.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String pad(String data, char pad, int length)  {
        return create(pad, length) + data + create(pad, length);
    }

        /**
         * <p>pad.</p>
         *
         * @param data a {@link String} object.
         * @param pad a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String pad(String data, String pad, int length)  {
        return create(pad, length) + data + create(pad, length);
    }

        /**
         * <p>padCenter.</p>
         *
         * @param data a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padCenter(String data, int length) {
        return padCenter(data, DEFAULT_PAD_CHAR, length);
    }

        /**
         * <p>padCenter.</p>
         *
         * @param data a {@link String} object.
         * @param pad a char.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String padCenter(String data, char pad, int length) {
        if(data.length() < length) {
            int needed = length - data.length();
            int padNeeded = needed / 2;
            StringBuilder result = new StringBuilder();
            result.append(create(pad, padNeeded));
            result.append(data);
            result.append(create(pad, padNeeded));
            int remaining = length - result.length();
            result.append(create(pad, remaining));
            return result.toString();
        }
        return data;
    }

        /**
         * <p>trimLeft.</p>
         *
         * @param data a {@link String} object.
         * @return a {@link String} object.
         */
        public static String trimLeft(String data)  {
        return trimLeft(data, DEFAULT_PAD_CHAR);
    }

        /**
         * <p>trimLeft.</p>
         *
         * @param data a {@link String} object.
         * @param trim a char.
         * @return a {@link String} object.
         */
        public static String trimLeft(String data, char trim)  {
        for(var index = 0; index < data.length(); index++)
            if(!(data.charAt(index) == trim))
                return data.substring(index);
        return EMPTY;
    }

        /**
         * <p>trimRight.</p>
         *
         * @param data a {@link String} object.
         * @return a {@link String} object.
         */
        public static String trimRight(String data)  {
        return trimRight(data, DEFAULT_PAD_CHAR);
    }

        /**
         * <p>trimRight.</p>
         *
         * @param data a {@link String} object.
         * @param trim a char.
         * @return a {@link String} object.
         */
        public static String trimRight(String data, char trim)  {
        int count = 0;
        for(var index = data.length(); index > 0; index--)
            if(data.charAt(index-1) == trim)
                count++;
            else
                return data.substring(0, data.length() - count);
        return EMPTY;
    }

        /**
         * <p>trim.</p>
         *
         * @param data a {@link String} object.
         * @return a {@link String} object.
         */
        public static String trim(String data)  {
        return trim(data, DEFAULT_PAD_CHAR);
    }

        /**
         * <p>trim.</p>
         *
         * @param data a {@link String} object.
         * @param trim a char.
         * @return a {@link String} object.
         */
        public static String trim(String data, char trim)  {
        var result = trimLeft(data, trim);
        return trimRight(result, trim);
    }

        /**
         * <p>center.</p>
         *
         * @param text a {@link String} object.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String center(String text, int length){
        var out = String.format("%"+length+"s%s%"+length+"s", "",text,"");
        var mid = (out.length()/2);
        var start = mid - (length/2);
        var end = start + length;
        return out.substring((int) start, (int) end);
    }

        /**
         * <p>concat.</p>
         *
         * @param data a {@link String} object.
         * @return a {@link String} object.
         */
        public static String concat(String ... data)  {
        var sb = new StringBuilder();
        for(var d : data){
            sb.append(d);
        }
        return sb.toString();
    }

        /**
         * <p>appendHexString.</p>
         *
         * @param builder a {@link StringBuilder} object.
         * @param byt a byte.
         */
        public static void appendHexString(StringBuilder builder, byte byt){
        builder.append(String.format("%02X", byt));
    }
        /**
         * <p>toHexString.</p>
         *
         * @param byt a byte.
         * @return a {@link String} object.
         */
        public static String toHexString(byte byt){
        return String.format("%02X", byt);
    }

        /**
         * <p>appendHexString.</p>
         *
         * @param builder a {@link StringBuilder} object.
         * @param byt a int.
         */
        public static void appendHexString(StringBuilder builder, int byt){
        builder.append(String.format("%02X", (byte)byt));
    }
        /**
         * <p>toHexString.</p>
         *
         * @param byt a int.
         * @return a {@link String} object.
         */
        public static String toHexString(int byt){
        return String.format("%02X", (byte)byt);
    }

        /**
         * <p>appendHexString.</p>
         *
         * @param builder a {@link StringBuilder} object.
         * @param bytes an array of {@link byte} objects.
         */
        public static void appendHexString(StringBuilder builder, byte[] bytes){
        for (byte b : bytes) {
            builder.append(String.format("%02X ", b));
        }
    }
        /**
         * <p>toHexString.</p>
         *
         * @param bytes an array of {@link byte} objects.
         * @return a {@link String} object.
         */
        public static String toHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        appendHexString(sb, bytes);
        return sb.toString().trim();
    }

        /**
         * <p>appendHexString.</p>
         *
         * @param builder a {@link StringBuilder} object.
         * @param buffer a {@link ByteBuffer} object.
         */
        public static void appendHexString(StringBuilder builder, ByteBuffer buffer){
        appendHexString(builder, buffer.array());
    }
        /**
         * <p>toHexString.</p>
         *
         * @param buffer a {@link ByteBuffer} object.
         * @return a {@link String} object.
         */
        public static String toHexString(ByteBuffer buffer){
        StringBuilder sb = new StringBuilder();
        appendHexString(sb, buffer);
        return sb.toString().trim();
    }

        /**
         * <p>appendHexString.</p>
         *
         * @param builder a {@link StringBuilder} object.
         * @param bytes an array of {@link byte} objects.
         * @param offset a int.
         * @param length a int.
         */
        public static void appendHexString(StringBuilder builder, byte[] bytes, int offset, int length){
        appendHexString(builder, Arrays.copyOfRange(bytes, offset, length));
    }
        /**
         * <p>toHexString.</p>
         *
         * @param bytes an array of {@link byte} objects.
         * @param offset a int.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String toHexString(byte[] bytes, int offset, int length){
        StringBuilder sb = new StringBuilder();
        appendHexString(sb, bytes, offset, length);
        return sb.toString().trim();
    }

        /**
         * <p>appendHexString.</p>
         *
         * @param builder a {@link StringBuilder} object.
         * @param buffer a {@link ByteBuffer} object.
         * @param offset a int.
         * @param length a int.
         */
        public static void appendHexString(StringBuilder builder, ByteBuffer buffer, int offset, int length){
        appendHexString(builder, buffer.array(), offset, length);
    }
        /**
         * <p>toHexString.</p>
         *
         * @param buffer a {@link ByteBuffer} object.
         * @param offset a int.
         * @param length a int.
         * @return a {@link String} object.
         */
        public static String toHexString(ByteBuffer buffer, int offset, int length){
        StringBuilder sb = new StringBuilder();
        appendHexString(sb, buffer, offset, offset+length);
        return sb.toString().trim();
    }


}
