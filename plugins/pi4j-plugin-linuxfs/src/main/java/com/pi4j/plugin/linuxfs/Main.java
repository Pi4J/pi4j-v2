package com.pi4j.plugin.linuxfs;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: LinuxFS I/O Providers
 * FILENAME      :  Main.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
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

import com.jcraft.jsch.*;
import com.pi4j.plugin.linuxfs.provider.gpio.LinuxCmd;

import java.io.InputStream;
import java.util.Arrays;

/**
 * <p>Main class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        //var output = connectAndExecute("pi", "rpi4b-1g", "raspberry", LinuxCmd.export(21));
        var output = sftp("pi", "rpi3bp", "raspberry", LinuxCmd.export(21));
        logger.info(output);
    }

    /**
     * <p>sftp.</p>
     *
     * @param user a {@link java.lang.String} object.
     * @param host a {@link java.lang.String} object.
     * @param password a {@link java.lang.String} object.
     * @param command1 a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String sftp(String user, String host, String password, String command1) {
        String CommandOutput = null;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();

            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            logger.info("Connected");

            Channel channel=session.openChannel("sftp");
            channel.connect();
            channel.setInputStream(null);
            ChannelSftp c=(ChannelSftp)channel;



//            InputStream is = new ByteArrayInputStream("20".getBytes());
//            c.put(is, "/sys/class/gpio/export");
            //logger.info(x);

//            var cont = c.stat("/sys/class/gpio/gpio5");
//            logger.info(cont);
//
//            c.ls("/sys/class/gpio").forEach(v->{
//                logger.info("-> " + v);
//            });

            //logger.info(c.lstat("/sys/class/gpio/gpio22"));

            //var ls = c.ls("/sys/class/gpio").contains("gpio20");
            //logger.info(ls);

            //c.get("/sys/class/gpio/gpio20", System.out);

//            InputStream is = new ByteArrayInputStream("out".getBytes());
//            c.put(is, "/sys/class/gpio/gpio20/direction");
//
//            c.put(new ByteArrayInputStream("1".getBytes()), "/sys/class/gpio/gpio20/value");



            InputStream in  = c.get("/dev/pigout");

            //InputStream in  = c.get("/sys/class/gpio/gpio5/value");
            logger.info(Arrays.toString(in.readAllBytes()));
//
//            byte[] tmp = new byte[1024];
//            while (true) {
//                while (in.available() > 0) {
//                    int i = in.read(tmp, 0, 1024);
//
//                    if (i < 0)
//                        break;
//                    // logger.info(new String(tmp, 0, i));
//                    CommandOutput = new String(tmp, 0, i);
//                }
//
//                if (channel.isClosed()) {
//                    // logger.info("exit-status: " +
//                    // channel.getExitStatus());
//                    break;
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception ee) {
//                }
//            }

            channel.disconnect();
            session.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommandOutput;
    }

    /**
     * <p>connectAndExecute.</p>
     *
     * @param user a {@link java.lang.String} object.
     * @param host a {@link java.lang.String} object.
     * @param password a {@link java.lang.String} object.
     * @param command1 a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String connectAndExecute(String user, String host, String password, String command1) {
        String CommandOutput = null;
        try {


            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();

            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            // logger.info("Connected");

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command1);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);

                    if (i < 0)
                        break;
                    // logger.info(new String(tmp, 0, i));
                    CommandOutput = new String(tmp, 0, i);
                }

                if (channel.isClosed()) {
                    // logger.info("exit-status: " +
                    // channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
            // logger.info("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommandOutput;

    }

}


