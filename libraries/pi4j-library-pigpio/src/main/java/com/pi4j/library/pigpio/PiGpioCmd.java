package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  PiGpioCmd.java
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

/**
 * <p>PiGpioCmd class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum PiGpioCmd {
    UNKNOWN(-1),
    MODES  (0),
    MODEG  (1),
    PUD    (2),
    READ   (3),
    WRITE  (4),
    PWM    (5),
    PRS    (6),
    PFS    (7),
    SERVO  (8),
    WDOG   (9),
    BR1   (10),
    BR2   (11),
    BC1   (12),
    BC2   (13),
    BS1   (14),
    BS2   (15),
    TICK  (16),
    HWVER (17),
    NO    (18),
    NB    (19),
    NP    (20),
    NC    (21),
    PRG   (22),
    PFG   (23),
    PRRG  (24),
    HELP  (25),
    PIGPV (26),
    WVCLR (27),
    WVAG  (28),
    WVAS  (29),
    WVGO  (30),
    WVGOR (31),
    WVBSY (32),
    WVHLT (33),
    WVSM  (34),
    WVSP  (35),
    WVSC  (36),
    TRIG  (37),
    PROC  (38),
    PROCD (39),
    PROCR (40),
    PROCS (41),
    SLRO  (42),
    SLR   (43),
    SLRC  (44),
    PROCP (45),
    MICS  (46),
    MILS  (47),
    PARSE (48),
    WVCRE (49),
    WVDEL (50),
    WVTX  (51),
    WVTXR (52),
    WVNEW (53),
    I2CO  (54),
    I2CC  (55),
    I2CRD (56),
    I2CWD (57),
    I2CWQ (58),
    I2CRS (59),
    I2CWS (60),
    I2CRB (61),
    I2CWB (62),
    I2CRW (63),
    I2CWW (64),
    I2CRK (65),
    I2CWK (66),
    I2CRI (67),
    I2CWI (68),
    I2CPC (69),
    I2CPK (70),
    SPIO  (71),
    SPIC  (72),
    SPIR  (73),
    SPIW  (74),
    SPIX  (75),
    SERO  (76),
    SERC  (77),
    SERRB (78),
    SERWB (79),
    SERR  (80),
    SERW  (81),
    SERDA (82),
    GDC   (83),
    GPW   (84),
    HC    (85),
    HP    (86),
    CF1   (87),
    CF2   (88),
    BI2CC (89),
    BI2CO (90),
    BI2CZ (91),
    I2CZ  (92),
    WVCHA (93),
    SLRI  (94),
    CGI   (95),
    CSI   (96),
    FG    (97),
    FN    (98),
    NOIB  (99),
    WVTXM (100),
    WVTAT (101),
    PADS  (102),
    PADG  (103),
    FO    (104),
    FC    (105),
    FR    (106),
    FW    (107),
    FS    (108),
    FL    (109),
    SHELL (110),
    BSPIC (111),
    BSPIO (112),
    BSPIX (113),
    BSCX  (114),
    EVM   (115),
    EVT   (116),
    PROCU (117);


    private int value;

    PiGpioCmd(int value){
        this.value  =value;
    }

    /**
     * <p>value.</p>
     *
     * @return a int.
     */
    public int value(){
        return this.value;
    }

    /**
     * <p>from.</p>
     *
     * @param value a {@link java.lang.Number} object.
     * @return a {@link com.pi4j.library.pigpio.PiGpioCmd} object.
     */
    public static PiGpioCmd from(Number value){
        for(PiGpioCmd c : PiGpioCmd.values()){
            if(c.value() == value.intValue()) return c;
        }
        return UNKNOWN;
    }



}
