package com.pi4j.util;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  Console.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Console class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private static final int LINE_WIDTH = 60;
    /** Constant <code>CLEAR_SCREEN_ESCAPE_SEQUENCE="\033[2J\033[1;1H"</code> */
    public static final String CLEAR_SCREEN_ESCAPE_SEQUENCE = "\033[2J\033[1;1H";
    /** Constant <code>ERASE_LINE_ESCAPE_SEQUENCE="\033[K"</code> */
    public static final String ERASE_LINE_ESCAPE_SEQUENCE = "\033[K";

    /** Constant <code>LINE_SEPARATOR_CHAR='*'</code> */
    public static final char LINE_SEPARATOR_CHAR = '*';
    /** Constant <code>LINE_SEPARATOR="StringUtil.repeat(LINE_SEPARATOR_CHAR, "{trunked}</code> */
    public static final String LINE_SEPARATOR = StringUtil.repeat(LINE_SEPARATOR_CHAR, LINE_WIDTH);

    protected boolean exiting = false;

    /**
     * <p>println.</p>
     *
     * @param format a {@link java.lang.String} object.
     * @param args a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console println(String format, Object ... args){
        return println(String.format(format, args));
    }

    /**
     * <p>print.</p>
     *
     * @param format a {@link java.lang.String} object.
     * @param args a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console print(String format, Object ... args){
        return print(String.format(format, args));
    }

    /**
     * <p>println.</p>
     *
     * @param line a {@link java.lang.String} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console println(String line){
        logger.info(line);
        return this;
    }

    /**
     * <p>println.</p>
     *
     * @param line a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console println(Object line){
        logger.info(line.toString());
        return this;
    }

    /**
     * <p>println.</p>
     *
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console println(){
        return println("");
    }

    /**
     * <p>print.</p>
     *
     * @param data a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console print(Object data){
        logger.info(data.toString());
        return this;
    }

    /**
     * <p>print.</p>
     *
     * @param data a {@link java.lang.String} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console print(String data){
        logger.info(data);
        return this;
    }

    /**
     * <p>println.</p>
     *
     * @param character a char.
     * @param repeat a int.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console println(char character, int repeat){
        return println(StringUtil.repeat(character, repeat));
    }

    /**
     * <p>emptyLine.</p>
     *
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console emptyLine(){
        return emptyLine(1);
    }

    /**
     * <p>emptyLine.</p>
     *
     * @param number a int.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console emptyLine(int number){
        for(var index = 0; index < number; index++){
            println();
        }
        return this;
    }

    /**
     * <p>separatorLine.</p>
     *
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console separatorLine(){
        return println(LINE_SEPARATOR);
    }

    /**
     * <p>separatorLine.</p>
     *
     * @param character a char.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console separatorLine(char character){
        return separatorLine(character, LINE_WIDTH);
    }

    /**
     * <p>separatorLine.</p>
     *
     * @param character a char.
     * @param length a int.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console separatorLine(char character, int length){
        return println(StringUtil.repeat(character, length));
    }

    /**
     * <p>title.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console title(String ... title){
        clearScreen().separatorLine().separatorLine().emptyLine();
        for(var s : title) {
            println(StringUtil.center(s, LINE_WIDTH));
        }
        emptyLine().separatorLine().separatorLine().emptyLine();
        return this;
    }

    /**
     * <p>box.</p>
     *
     * @param lines a {@link java.lang.String} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console box(String ... lines) {
        return box(2, lines);
    }

    /**
     * <p>box.</p>
     *
     * @param padding a int.
     * @param lines a {@link java.lang.String} object.
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console box(int padding, String ... lines) {
        int max_length = 0;
        for(var l : lines) {
            if (l.length() > max_length) {
                max_length = l.length();
            }
        }
        separatorLine('-', max_length + padding * 2 + 2);
        var left  = StringUtil.padRight("|", padding);
        var right = StringUtil.padLeft("|", padding);
        for(var l : lines){
            println(StringUtil.concat(left, StringUtil.padRight(l, max_length - l.length()), right));
        }
        separatorLine('-', max_length + padding * 2 + 2);
        return this;
    }

    /**
     * <p>goodbye.</p>
     *
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console goodbye() {
        emptyLine();
        separatorLine();
        println(StringUtil.center("GOODBYE", LINE_WIDTH));
        separatorLine();
        emptyLine();
        return this;
    }

    /**
     * <p>clearScreen.</p>
     *
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console clearScreen(){
        return print(CLEAR_SCREEN_ESCAPE_SEQUENCE);
    }

    /**
     * <p>eraseLine.</p>
     *
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console eraseLine(){
        return print(ERASE_LINE_ESCAPE_SEQUENCE);
    }

    /**
     * <p>promptForExit.</p>
     *
     * @return a {@link com.pi4j.util.Console} object.
     */
    public synchronized Console promptForExit(){
        box(4, "PRESS CTRL-C TO EXIT");
        emptyLine();
        exiting = false;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                exiting = true;
                goodbye();
            }
        });
        return this;
    }

    /**
     * <p>waitForExit.</p>
     *
     * @throws java.lang.InterruptedException if any.
     */
    public void waitForExit() throws InterruptedException {
        while(!exiting){
            Thread.sleep(50);
        }
    }

    /**
     * <p>exiting.</p>
     *
     * @return a boolean.
     */
    public synchronized boolean exiting(){
        return exiting;
    }
    /**
     * <p>isRunning.</p>
     *
     * @return a boolean.
     */
    public synchronized boolean isRunning(){
        return !exiting;
    }
}
