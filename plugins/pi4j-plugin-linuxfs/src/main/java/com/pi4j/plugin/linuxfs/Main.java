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

import com.jcraft.jsch.*;
import com.pi4j.plugin.linuxfs.provider.gpio.LinuxCmd;

import java.io.InputStream;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {



        //var output = connectAndExecute("pi", "rpi4b-1g", "raspberry", LinuxCmd.export(21));

        var output = sftp("pi", "rpi3bp", "raspberry", LinuxCmd.export(21));


        System.out.println(output);
    }

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
            // System.out.println("Connected");

            Channel channel=session.openChannel("sftp");
            channel.connect();
            channel.setInputStream(null);
            ChannelSftp c=(ChannelSftp)channel;



//            InputStream is = new ByteArrayInputStream("20".getBytes());
//            c.put(is, "/sys/class/gpio/export");
            //System.out.println(x);

//            var cont = c.stat("/sys/class/gpio/gpio5");
//            System.out.println(cont);
//
//            c.ls("/sys/class/gpio").forEach(v->{
//                System.out.println("-> " + v);
//            });

            //System.out.println(c.lstat("/sys/class/gpio/gpio22"));

            //var ls = c.ls("/sys/class/gpio").contains("gpio20");
            //System.out.println(ls);

            //c.get("/sys/class/gpio/gpio20", System.out);

//            InputStream is = new ByteArrayInputStream("out".getBytes());
//            c.put(is, "/sys/class/gpio/gpio20/direction");
//
//            c.put(new ByteArrayInputStream("1".getBytes()), "/sys/class/gpio/gpio20/value");



            InputStream in  = c.get("/dev/pigout");

            //InputStream in  = c.get("/sys/class/gpio/gpio5/value");
            System.out.println(Arrays.toString(in.readAllBytes()));
//
//            byte[] tmp = new byte[1024];
//            while (true) {
//                while (in.available() > 0) {
//                    int i = in.read(tmp, 0, 1024);
//
//                    if (i < 0)
//                        break;
//                    // System.out.print(new String(tmp, 0, i));
//                    CommandOutput = new String(tmp, 0, i);
//                }
//
//                if (channel.isClosed()) {
//                    // System.out.println("exit-status: " +
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
            // System.out.println("Connected");

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
                    // System.out.print(new String(tmp, 0, i));
                    CommandOutput = new String(tmp, 0, i);
                }

                if (channel.isClosed()) {
                    // System.out.println("exit-status: " +
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
            // System.out.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommandOutput;

    }

}


