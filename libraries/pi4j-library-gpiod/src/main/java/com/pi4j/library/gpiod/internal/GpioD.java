package com.pi4j.library.gpiod.internal;

import com.pi4j.library.gpiod.util.NativeLibraryLoader;

/**
 * <p>GpioD interface.</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioD {
    // C library: https://git.kernel.org/pub/scm/libs/libgpiod/libgpiod.git/tree/include/gpiod.h?h=v1.6.x

    private GpioD() {
        // forbid object construction
    }

    static {
        // Load the platform library
        NativeLibraryLoader.load("libgpiod.so", "pi4j-gpiod");
        NativeLibraryLoader.load("libpi4j-gpiod.so", "pi4j-gpiod");
    }

    static GpioChip chipOpen(String path) {
        Long ptr = c_gpiod_chip_open(path);
        if (ptr == null)
            throw new GpioDException("c_gpiod_chip_open failed!");
        return new GpioChip(ptr);
    }

    private static native Long c_gpiod_chip_open(String path);

    static void chipClose(long chipPtr) {
        c_gpiod_chip_close(chipPtr);
    }

    private static native void c_gpiod_chip_close(long chipPtr);

    static String chipGetName(long chipPtr) {
        return c_gpiod_chip_name(chipPtr);
    }

    private static native String c_gpiod_chip_name(long chipPtr);

    static String chipGetLabel(long chipPtr) {
        return c_gpiod_chip_label(chipPtr);
    }

    private static native String c_gpiod_chip_label(long chipPtr);

    static int chipGetNumLines(long chipPtr) {
        return c_gpiod_chip_num_lines(chipPtr);
    }

    private static native int c_gpiod_chip_num_lines(long chipPtr);

    static long chipGetLine(long chipPtr, int offset) {
        Long linePtr = c_gpiod_chip_get_line(chipPtr, offset);
        if (linePtr == null)
            throw new GpioDException("c_gpiod_chip_get_line failed!");
        return linePtr;
    }

    private static native Long c_gpiod_chip_get_line(long chipPtr, int offset);

    static long chipGetLine(long chipPtr, String name) {
        Long linePtr = c_gpiod_chip_find_line(chipPtr, name);
        if (linePtr == null)
            throw new GpioDException("c_gpiod_chip_find_line failed!");
        return linePtr;
    }

    private static native Long c_gpiod_chip_find_line(long chipPtr, String name);

    static int lineGetOffset(long linePtr) {
        return c_gpiod_line_offset(linePtr);
    }

    private static native int c_gpiod_line_offset(long linePtr);

    static String lineGetName(long linePtr) {
        return c_gpiod_line_name(linePtr);
    }

    private static native String c_gpiod_line_name(long linePtr);

    static String lineGetConsumer(long linePtr) {
        return gpiod_line_consumer(linePtr);
    }

    private static native String gpiod_line_consumer(long linePtr);

    static LineDirection lineGetDirection(long linePtr) {
        return LineDirection.fromInt(c_gpiod_line_direction(linePtr));
    }

    private static native int c_gpiod_line_direction(long linePtr);

    static LineActiveState lineGetActiveState(long linePtr) {
        return LineActiveState.fromInt(c_gpiod_line_active_state(linePtr));
    }

    private static native int c_gpiod_line_active_state(long linePtr);

    static LineBias lineGetBias(long linePtr) {
        return LineBias.fromInt(c_gpiod_line_bias(linePtr));
    }

    private static native int c_gpiod_line_bias(long linePtr);

    static boolean lineIsUsed(long linePtr) {
        return c_gpiod_line_is_used(linePtr);
    }

    private static native boolean c_gpiod_line_is_used(long linePtr);

    static boolean lineIsOpenDrain(long linePtr) {
        return c_gpiod_line_is_open_drain(linePtr);
    }

    private static native boolean c_gpiod_line_is_open_drain(long linePtr);

    static boolean lineIsOpenSource(long linePtr) {
        return c_gpiod_line_is_open_source(linePtr);
    }

    private static native boolean c_gpiod_line_is_open_source(long linePtr);

    static void lineUpdate(long linePtr) {
        int result = c_gpiod_line_update(linePtr);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_update failed: " + result);
    }

    private static native int c_gpiod_line_update(long linePtr);

    static void lineRequest(long linePtr, long lineRequestPtr, int defaultVal) {
        int result = c_gpiod_line_request(linePtr, lineRequestPtr, defaultVal);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request failed: " + result);
    }

    private static native int c_gpiod_line_request(long linePtr, long lineRequestPtr, int default_val);

    static void lineRequestInput(long linePtr, String consumer) {
        int result = c_gpiod_line_request_input(linePtr, consumer);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_input failed: " + result);
    }

    private static native int c_gpiod_line_request_input(long linePtr, String consumer);

    static void lineRequestOutput(long linePtr, String consumer, int defaultVal) {
        int result = c_gpiod_line_request_output(linePtr, consumer, defaultVal);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_output failed: " + result);
    }

    private static native int c_gpiod_line_request_output(long linePtr, String consumer, int default_val);

    static void lineRequestRisingEdgeEvents(long linePtr, String consumer) {
        int result = c_gpiod_line_request_rising_edge_events(linePtr, consumer);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_rising_edge_events failed: " + result);
    }

    private static native int c_gpiod_line_request_rising_edge_events(long linePtr, String consumer);

    static void lineRequestFallingEdgeEvents(long linePtr, String consumer) {
        int result = c_gpiod_line_request_falling_edge_events(linePtr, consumer);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_falling_edge_events failed: " + result);
    }

    private static native int c_gpiod_line_request_falling_edge_events(long linePtr, String consumer);

    static void lineRequestBothEdgeEvents(long linePtr, String consumer) {
        int result = c_gpiod_line_request_both_edges_events(linePtr, consumer);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_both_edges_events failed: " + result);
    }

    private static native int c_gpiod_line_request_both_edges_events(long linePtr, String consumer);

    static void lineRequestInputFlags(long linePtr, String consumer, int flags) {
        int result = c_gpiod_line_request_input_flags(linePtr, consumer, flags);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_input_flags failed: " + result);
    }

    private static native int c_gpiod_line_request_input_flags(long linePtr, String consumer, int flags);

    static void lineRequestOutputFlags(long linePtr, String consumer, int flags, int defaultVal) {
        int result = c_gpiod_line_request_output_flags(linePtr, consumer, flags, defaultVal);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_output_flags failed: " + result);
    }

    private static native int c_gpiod_line_request_output_flags(long linePtr, String consumer, int flags,
        int default_val);

    static void lineRequestRisingEdgeEventsFlags(long linePtr, String consumer, int flags) {
        int result = c_gpiod_line_request_rising_edge_events_flags(linePtr, consumer, flags);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_rising_edge_events_flags failed: " + result);
    }

    private static native int c_gpiod_line_request_rising_edge_events_flags(long linePtr, String consumer, int flags);

    static void lineRequestFallingEdgeEventsFlags(long linePtr, String consumer, int flags) {
        int result = c_gpiod_line_request_falling_edge_events_flags(linePtr, consumer, flags);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_falling_edge_events_flags failed: " + result);
    }

    private static native int c_gpiod_line_request_falling_edge_events_flags(long linePtr, String consumer, int flags);

    static void lineRequestBothEdgeEventsFlags(long linePtr, String consumer, int flags) {
        int result = c_gpiod_line_request_both_edges_events_flags(linePtr, consumer, flags);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_request_both_edges_events_flags failed: " + result);
    }

    private static native int c_gpiod_line_request_both_edges_events_flags(long linePtr, String consumer, int flags);

    static void lineRelease(long linePtr) {
        c_gpiod_line_release(linePtr);
    }

    private static native void c_gpiod_line_release(long linePtr);

    static boolean lineIsRequested(long linePtr) {
        return c_gpiod_line_is_requested(linePtr);
    }

    private static native boolean c_gpiod_line_is_requested(long linePtr);

    static boolean lineIsFree(long linePtr) {
        return c_gpiod_line_is_free(linePtr);
    }

    private static native boolean c_gpiod_line_is_free(long linePtr);

    static int lineGetValue(long linePtr) {
        int result = c_gpiod_line_get_value(linePtr);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_get_value failed: " + result);
        return result;
    }

    private static native int c_gpiod_line_get_value(long linePtr);

    static void lineSetValue(long linePtr, int value) {
        int result = c_gpiod_line_set_value(linePtr, value);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_set_value failed: " + result);
    }

    private static native int c_gpiod_line_set_value(long linePtr, int value);

    static void lineSetConfig(long linePtr, LineRequest direction, int flags, int value) {
        int result = c_gpiod_line_set_config(linePtr, direction.val, flags, value);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_set_config failed: " + result);
    }

    private static native int c_gpiod_line_set_config(long linePtr, int direction, int flags, int value);

    static void lineSetFlags(long linePtr, int flags) {
        int result = c_gpiod_line_set_flags(linePtr, flags);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_set_flags failed: " + result);
    }

    private static native int c_gpiod_line_set_flags(long linePtr, int flags);

    static void lineSetDirectionInput(long linePtr) {
        int result = c_gpiod_line_set_direction_input(linePtr);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_set_direction_input failed: " + result);
    }

    private static native int c_gpiod_line_set_direction_input(long linePtr);

    static void lineSetDirectionOutput(long linePtr, int value) {
        int result = c_gpiod_line_set_direction_output(linePtr, value);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_set_direction_output failed: " + result);
    }

    private static native int c_gpiod_line_set_direction_output(long linePtr, int value);

    static boolean lineEventWait(long linePtr, long timeoutNs) {
        int result = c_gpiod_line_event_wait(linePtr, timeoutNs);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_event_wait failed: " + result);
        return result > 0;
    }

    private static native int c_gpiod_line_event_wait(long linePtr, long timeoutNs);

    static boolean lineEventRead(long linePtr, long lineEventPtr) {
        int result = c_gpiod_line_event_read(linePtr, lineEventPtr);
        if (result < 0)
            throw new GpioDException("c_gpiod_line_event_read failed: " + result);
        return result > 0;
    }

    private static native int c_gpiod_line_event_read(long linePtr, long eventPtr);

    static long lineGet(String device, int offset) {
        Long ptr = c_gpiod_line_get(device, offset);
        if (ptr == null)
            throw new GpioDException("c_gpiod_line_get failed!");
        return ptr;
    }

    private static native Long c_gpiod_line_get(String device, int offset);

    static long lineFind(String name) {
        Long ptr = gpiod_line_find(name);
        if (ptr == null)
            throw new GpioDException("gpiod_line_find failed!");
        return ptr;
    }

    private static native Long gpiod_line_find(String name);

    static void lineCloseChip(long linePtr) {
        c_gpiod_line_close_chip(linePtr);
    }

    private static native void c_gpiod_line_close_chip(long linePtr);

    static long lineGetChip(long linePtr) {
        return gpiod_line_get_chip(linePtr);
    }

    private static native long gpiod_line_get_chip(long linePtr);

    static long chipIterNew() {
        Long ptr = gpiod_chip_iter_new();
        if (ptr == null)
            throw new GpioDException("gpiod_chip_iter_new failed!");
        return ptr;
    }

    private static native Long gpiod_chip_iter_new();

    static void chipIterFree(long chipIterPtr) {
        c_gpiod_chip_iter_free(chipIterPtr);
    }

    private static native void c_gpiod_chip_iter_free(long chipIterPtr);

    static void chipIterFreeNoClose(long chipIterPtr) {
        c_gpiod_chip_iter_free_noclose(chipIterPtr);
    }

    private static native void c_gpiod_chip_iter_free_noclose(long chipIterPtr);

    private static native Long c_gpiod_chip_iter_next(long chipIterPtr);

    static Long chipIterNextNoClose(long chipIterPtr) {
        return c_gpiod_chip_iter_next_noclose(chipIterPtr);
    }

    private static native Long c_gpiod_chip_iter_next_noclose(long chipIterPtr);

    static long lineIterNew(long chipPtr) {
        Long ptr = gpiod_line_iter_new(chipPtr);
        if (ptr == null)
            throw new GpioDException("gpiod_line_iter_new failed!");
        return ptr;
    }

    private static native Long gpiod_line_iter_new(long chipPtr);

    private static native Long gpiod_line_iter_next(long lineIterPtr);

    static long lineEventGetTimespec(long lineEventPtr) {
        return c_gpiod_line_event_get_timespec(lineEventPtr);
    }

    private static native long c_gpiod_line_event_get_timespec(long lineEventPtr);

    static LineEvent lineEventGetType(long lineEventPtr) {
        return LineEvent.fromInt(c_gpiod_line_event_get_type(lineEventPtr));
    }

    private static native int c_gpiod_line_event_get_type(long lineEventPtr);

    static long lineEventNew() {
        Long ptr = c_gpiod_line_event_new();
        if (ptr == null)
            throw new GpioDException("c_gpiod_line_event_new failed!");
        return ptr;
    }

    private static native Long c_gpiod_line_event_new();

    static void lineEventFree(long eventPtr) {
        c_gpiod_line_event_free(eventPtr);
    }

    private static native void c_gpiod_line_event_free(long eventPtr);

    static String getVersion() {
        return c_gpiod_version_string();
    }

    private static native String c_gpiod_version_string();
}
