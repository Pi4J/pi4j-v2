package com.pi4j.boardinfo.definition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.pi4j.boardinfo.definition.BoardType.*;

/**
 * Partially based on
 * <a href="https://en.wikipedia.org/wiki/Raspberry_Pi#Specifications">en.wikipedia.org/wiki/Raspberry_Pi</a>
 * <a href="https://oastic.com/posts/how-to-know-which-raspberry-do-you-have/">oastic.com/posts/how-to-know-which-raspberry-do-you-have</a>
 * <a href="https://www.raspberrypi.com/documentation/computers/raspberry-pi.html#new-style-revision-codes-in-use">raspberrypi.com/documentation/computers/raspberry-pi.html#new-style-revision-codes-in-use</a>
 * <a href="https://www.raspberrypi-spy.co.uk/2012/09/checking-your-raspberry-pi-board-version/">raspberrypi-spy.co.uk/2012/09/checking-your-raspberry-pi-board-version/</a>
 */
public enum BoardModel {
    MODEL_1_A("Raspberry Pi 1 Model A", SINGLE_BOARD_COMPUTER,
            new ArrayList<>(),
            PiModel.MODEL_A,
            HeaderVersion.TYPE_1,
            LocalDate.of(2013, 2, 1),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(700),
            Collections.singletonList(256 * 1024)),
    MODEL_1_A_PLUS("Raspberry Pi 1 Model A+", SINGLE_BOARD_COMPUTER,
            Collections.singletonList("900021"),
            PiModel.MODEL_A,
            HeaderVersion.TYPE_1,
            LocalDate.of(2014, 11, 1),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(700),
            Arrays.asList(256 * 1024, 512 * 1024),
            Collections.singletonList("Amount of memory changed to 512Mb on 20160810")),
    MODEL_3_A_PLUS("Raspberry Pi 3 Model A+", SINGLE_BOARD_COMPUTER,
            Collections.singletonList("9020e0"),
            PiModel.MODEL_A,
            HeaderVersion.TYPE_3,
            LocalDate.of(2018, 11, 1),
            Soc.BCM2837B0,
            Cpu.CORTEX_A53, 4,
            Collections.singletonList(1400),
            Collections.singletonList(512 * 1024)),
    MODEL_1_B("Raspberry Pi 1 Model B", SINGLE_BOARD_COMPUTER,
            new ArrayList<>(),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_1,
            LocalDate.of(2012, 4, 1),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(700),
            Arrays.asList(256 * 1024, 512 * 1024),
            Collections.singletonList("Amount of memory changed to 512Mb on 20121015")),
    MODEL_1_B_PLUS("Raspberry Pi 1 Model B+", SINGLE_BOARD_COMPUTER,
            Collections.singletonList("900032"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_1,
            LocalDate.of(2014, 7, 1),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(700),
            Collections.singletonList(512 * 1024)),
    MODEL_2_B("Raspberry Pi 2 Model B", SINGLE_BOARD_COMPUTER,
            Arrays.asList("a01040", "a01041", "a21041"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_2,
            LocalDate.of(2015, 2, 1),
            Soc.BCM2836,
            Cpu.CORTEX_A7, 4,
            Collections.singletonList(900),
            Collections.singletonList(1024 * 1024)),
    MODEL_2_B_V1_2("Raspberry Pi 2 Model B V1.2", SINGLE_BOARD_COMPUTER,
            Arrays.asList("a02042", "a22042"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_2,
            LocalDate.of(2016, 10, 1),
            Soc.BCM2837,
            Cpu.CORTEX_A53, 4,
            Collections.singletonList(900),
            Collections.singletonList(1024 * 1024)),
    MODEL_3_B("Raspberry Pi 3 Model B", SINGLE_BOARD_COMPUTER,
            Arrays.asList("a02082", "a22082", "a32082", "a52082", "a22083"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_3,
            LocalDate.of(2016, 2, 1),
            Soc.BCM2837,
            Cpu.CORTEX_A53, 4,
            Collections.singletonList(1200),
            Collections.singletonList(1024 * 1024)),
    MODEL_3_B_PLUS("Raspberry Pi 3 Model B+", SINGLE_BOARD_COMPUTER,
            Collections.singletonList("a020d3"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_3,
            LocalDate.of(2018, 3, 14),
            Soc.BCM2837B0,
            Cpu.CORTEX_A53, 4,
            Collections.singletonList(1400),
            Collections.singletonList(1024 * 1024)),
    MODEL_4_B("Raspberry Pi 4 Model B", SINGLE_BOARD_COMPUTER,
            Arrays.asList("a03111", "b03111", "b03112", "b03114", "b03115", "c03111", "c03112", "c03114", "c03115", "d03114", "d03115"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_3,
            LocalDate.of(2019, 6, 24),
            Soc.BCM2711,
            Cpu.CORTEX_A72, 4,
            Arrays.asList(1500, 1800),
            Arrays.asList(1024 * 1024, 2048 * 1024, 4096 * 1024, 8192 * 1024)),
    MODEL_400("Raspberry Pi 400", ALL_IN_ONE_COMPUTER,
            Collections.singletonList("c03130"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_3,
            LocalDate.of(2020, 11, 2),
            Soc.BCM2711C0,
            Cpu.CORTEX_A72, 4,
            Collections.singletonList(1800),
            Collections.singletonList(4096 * 1024)),
    MODEL_5_B("Raspberry Pi 5 Model B", SINGLE_BOARD_COMPUTER,
            Arrays.asList("c04170", "d04170"),
            PiModel.MODEL_B,
            HeaderVersion.TYPE_3,
            LocalDate.of(2023, 9, 28),
            Soc.BCM2712,
            Cpu.CORTEX_A76, 4,
            Collections.singletonList(2400),
            Arrays.asList(4096 * 1024, 8192 * 1024)),
    COMPUTE_1("Compute Module 1", STACK_ON_COMPUTER,
            Collections.singletonList("900061"),
            PiModel.COMPUTE,
            HeaderVersion.COMPUTE,
            LocalDate.of(2014, 4, 1),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(700),
            Collections.singletonList(512 * 1024)),
    COMPUTE_3("Compute Module 3", STACK_ON_COMPUTER,
            Arrays.asList("a020a0", "a220a0"),
            PiModel.COMPUTE,
            HeaderVersion.COMPUTE,
            LocalDate.of(2017, 1, 1),
            Soc.BCM2837,
            Cpu.CORTEX_A53, 4,
            Collections.singletonList(1200),
            Collections.singletonList(1024 * 1024)),
    COMPUTE_3_PLUS("Compute Module 3+", STACK_ON_COMPUTER,
            Collections.singletonList("a02100"),
            PiModel.COMPUTE,
            HeaderVersion.COMPUTE,
            LocalDate.of(2019, 1, 1),
            Soc.BCM2837B0,
            Cpu.CORTEX_A53, 4,
            Collections.singletonList(1200),
            Collections.singletonList(1024 * 1024)),
    COMPUTE_4("Compute Module 4", STACK_ON_COMPUTER,
            Arrays.asList("a03140", "b03140", "c03140", "d03140"),
            PiModel.COMPUTE,
            HeaderVersion.COMPUTE,
            LocalDate.of(2020, 10, 1),
            Soc.BCM2711,
            Cpu.CORTEX_A72, 4,
            Collections.singletonList(1500),
            Arrays.asList(1024 * 1024, 2048 * 1024, 4096 * 1024, 8192 * 1024)),
    // https://datasheets.raspberrypi.com/cm4s/cm4s-datasheet.pdf
    COMPUTE_4S("Compute Module 4 SODIMM", STACK_ON_COMPUTER,
        new ArrayList<>(), // Not known yet
        PiModel.COMPUTE,
        HeaderVersion.COMPUTE,
        LocalDate.of(2020, 10, 1),
        Soc.BCM2711,
        Cpu.CORTEX_A72, 4,
        Collections.singletonList(1500),
        Arrays.asList(1024 * 1024, 2048 * 1024, 4096 * 1024, 8192 * 1024)),
    ZERO_PCB_1_2("Raspberry Pi Zero PCB V1.2", SINGLE_BOARD_COMPUTER,
            Arrays.asList("900092", "920092"),
            PiModel.ZERO,
            HeaderVersion.TYPE_3,
            LocalDate.of(2015, 11, 1),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(1000),
            Collections.singletonList(512 * 1024)),
    ZERO_PCB_1_3("Raspberry Pi Zero PCB V1.3", SINGLE_BOARD_COMPUTER,
            Arrays.asList("900093", "920093"),
            PiModel.ZERO,
            HeaderVersion.TYPE_3,
            LocalDate.of(2016, 5, 1),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(1000),
            Collections.singletonList(512 * 1024)),
    ZERO_W("Raspberry Pi Zero W", SINGLE_BOARD_COMPUTER,
            Collections.singletonList("9000c1"),
            PiModel.ZERO,
            HeaderVersion.TYPE_3,
            LocalDate.of(2017, 2, 28),
            Soc.BCM2835,
            Cpu.ARM1176JZF_S, 1,
            Collections.singletonList(1000),
            Collections.singletonList(512 * 1024)),
    ZERO_V2("Raspberry Pi Zero V2", SINGLE_BOARD_COMPUTER,
            Collections.singletonList("902120"),
            PiModel.ZERO,
            HeaderVersion.TYPE_3,
            LocalDate.of(2021, 10, 28),
            Soc.BCM2710A1,
            Cpu.CORTEX_A53, 4,
            Collections.singletonList(1000),
            Collections.singletonList(512 * 1024)),
    PICO("Raspberry Pi Pico", MICROCONTROLLER,
            new ArrayList<>(),
            PiModel.PICO,
            HeaderVersion.PICO,
            LocalDate.of(2021, 1, 1),
            Soc.RP2040,
            Cpu.CORTEX_MO_PLUS, 1,
            Collections.singletonList(1000),
            Collections.singletonList(264 + 2048)),
    PICO_W("Raspberry Pi Pico W", MICROCONTROLLER,
            new ArrayList<>(),
            PiModel.PICO,
            HeaderVersion.PICO,
            LocalDate.of(2022, 6, 1),
            Soc.RP2040,
            Cpu.CORTEX_MO_PLUS, 1,
            Collections.singletonList(1000),
            Collections.singletonList(264 + 2048),
            Collections.singletonList("Same form factor as PICO but with Wi-Fi")),
    UNKNOWN("Unknown", BoardType.UNKNOWN,
            new ArrayList<>(),
            PiModel.UNKNOWN,
            HeaderVersion.UNKNOWN,
            null,
            Soc.UNKNOWN,
            Cpu.UNKNOWN, 0,
            new ArrayList<>(),
            new ArrayList<>());

    private static final Logger logger = LoggerFactory.getLogger(BoardModel.class);

    private final String label;
    private final BoardType boardType;
    private final List<String> boardCodes;
    private final PiModel model;
    private final HeaderVersion headerVersion;
    private final LocalDate releaseDate;
    private final Soc soc;
    private final Cpu cpu;
    private final Integer numberOfCpu;
    private final List<Integer> versionsProcessorSpeedInMhz;
    private final List<Integer> versionsMemoryInKb;
    private final List<String> remarks;

    BoardModel(String label, BoardType boardType, List<String> boardCodes,
               PiModel model, HeaderVersion headerVersion, LocalDate releaseDate,
               Soc soc, Cpu cpu, Integer numberOfCpu,
               List<Integer> versionsProcessorSpeedInMhz, List<Integer> versionsMemoryInKb) {
        this(label, boardType, boardCodes, model, headerVersion, releaseDate, soc, cpu, numberOfCpu, versionsProcessorSpeedInMhz,
                versionsMemoryInKb, new ArrayList<>());
    }

    BoardModel(String label, BoardType boardType, List<String> boardCodes,
               PiModel model, HeaderVersion headerVersion, LocalDate releaseDate,
               Soc soc, Cpu cpu, Integer numberOfCpu,
               List<Integer> versionsProcessorSpeedInMhz, List<Integer> versionsMemoryInKb,
               List<String> remarks) {
        this.label = label;
        this.boardType = boardType;
        this.boardCodes = boardCodes;
        this.model = model;
        this.headerVersion = headerVersion;
        this.releaseDate = releaseDate;
        this.soc = soc;
        this.cpu = cpu;
        this.numberOfCpu = numberOfCpu;
        this.versionsProcessorSpeedInMhz = versionsProcessorSpeedInMhz;
        this.versionsMemoryInKb = versionsMemoryInKb;
        this.remarks = remarks;
    }

    public static BoardModel getByBoardCode(String boardCode) {
        var matches = Arrays.stream(BoardModel.values())
                .filter(bm -> bm.boardCodes.contains(boardCode))
                .collect(Collectors.toList());
        if (matches.isEmpty()) {
            return BoardModel.UNKNOWN;
        } else if (matches.size() > 1) {
            logger.error("Too many matching models found for code {}, probably an error in the definitions", boardCode);
        }
        return matches.get(0);
    }

    public static BoardModel getByBoardName(String boardName) {
        var matches = Arrays.stream(BoardModel.values())
                .filter(bm -> boardName.toLowerCase().startsWith(bm.label.toLowerCase()))
                .collect(Collectors.toList());
        if (matches.isEmpty()) {
            return BoardModel.UNKNOWN;
        } else if (matches.size() > 1) {
            logger.error("Too many matching models found for name {}, the given name is not exclusive enough", boardName);
        }
        return matches.get(0);
    }

    public String getName() {
        return name();
    }

    public String getLabel() {
        return label;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public List<String> getBoardCodes() {
        return boardCodes;
    }

    public PiModel getModel() {
        return model;
    }

    public HeaderVersion getHeaderVersion() {
        return headerVersion;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Soc getSoc() {
        return soc;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public Integer getNumberOfCpu() {
        return numberOfCpu;
    }

    public List<Integer> getVersionsProcessorSpeedInMhz() {
        return versionsProcessorSpeedInMhz;
    }

    public List<Integer> getVersionsMemoryInKb() {
        return versionsMemoryInKb;
    }

    public List<Float> getVersionsMemoryInMb() {
        return versionsMemoryInKb.stream().map(m -> m / 1024F).collect(Collectors.toList());
    }

    public List<Float> getVersionsMemoryInGb() {
        return versionsMemoryInKb.stream().map(m -> m / 1024F / 1024F).collect(Collectors.toList());
    }

    public List<String> getRemarks() {
        return remarks;
    }
}
