
#include <gpiod.h>
#include <stdint.h>
#include "com_pi4j_library_gpiod_internal_GpioD.h"

// Compile using:
// gcc -I /usr/lib/jvm/java-11-openjdk-amd64/include/ -I /usr/lib/jvm/java-11-openjdk-amd64/include/linux/ -lgpiod -I . -c com_pi4j_library_gpiod_internal_GpioD.c

JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1open
  (JNIEnv* env, jclass javaClass, jstring path) {
    struct gpiod_chip* chip;
    const char* nativeString = (*env)->GetStringUTFChars(env, path, NULL);
    chip = gpiod_chip_open(nativeString);
    (*env)->ReleaseStringUTFChars(env, path, nativeString);

    if(chip == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) chip);
}

JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1close
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    gpiod_chip_close((struct gpiod_chip*) (uintptr_t) chipPtr);
  }

JNIEXPORT jstring JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1name
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    const char* name = gpiod_chip_name((struct gpiod_chip*) (uintptr_t) chipPtr);
    jstring jStrName = (*env)->NewStringUTF(env, name);
    return jStrName;
  }

JNIEXPORT jstring JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1label
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    const char* label = gpiod_chip_label((struct gpiod_chip*) (uintptr_t) chipPtr);
    jstring jStrName = (*env)->NewStringUTF(env, label);
    return jStrName;
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1num_1lines
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    unsigned int num_lines = gpiod_chip_num_lines((struct gpiod_chip*) (uintptr_t) chipPtr);
    return num_lines;
}

JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1get_1line
  (JNIEnv* env, jclass javaClass, jlong chipPtr, jint offset) {
    struct gpiod_line* line;
    line = gpiod_chip_get_line((struct gpiod_chip*) (uintptr_t) chipPtr, offset);
    if(line == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env,"java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>","(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) line);
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1get_1lines
  (JNIEnv* env, jclass javaClass, jlong chipPtr, jintArray offsets, jint num_offsets, jlong lineBulkPtr) {
    jint* c_offsets = (*env)->GetIntArrayElements(env, offsets, 0);
    int returnVal = gpiod_chip_get_lines((struct gpiod_chip*) (uintptr_t) chipPtr, (unsigned int*) c_offsets, num_offsets, (struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr);
    (*env)->ReleaseIntArrayElements(env, offsets, c_offsets, 0);
    return returnVal;
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1get_1all_1lines
  (JNIEnv* env, jclass javaClass, jlong chipPtr, jlong lineBulkPtr) {
    return gpiod_chip_get_all_lines((struct gpiod_chip*) (uintptr_t) chipPtr, (struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr);
}

JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1find_1line
  (JNIEnv* env, jclass javaClass, jlong chipPtr, jstring name) {
  const char* c_name = (*env)->GetStringUTFChars(env, name, NULL);
  struct gpiod_line* line = gpiod_chip_find_line((struct gpiod_chip*) (uintptr_t) chipPtr, c_name);
  (*env)->ReleaseStringUTFChars(env, name, c_name);

  if(line == NULL) {
    return NULL;
  }
  jclass cls = (*env)->FindClass(env, "java/lang/Long");
  jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>","(J)V");
  return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) line);
}

JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1bulk_1free
  (JNIEnv* env, jclass javaClass, jlong bulkPtr) {
    free((struct gpiod_line_bulk*) (uintptr_t) bulkPtr);
}

JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1line_1bulk_1init
  (JNIEnv* env, jclass javaClass, jlong bulkPtr) {
    gpiod_line_bulk_init((struct gpiod_line_bulk*) (uintptr_t) bulkPtr);
}

JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1bulk_1new
  (JNIEnv* env, jclass javaClass) {
    struct gpiod_line_bulk* bulkPtr = (struct gpiod_line_bulk*) malloc(sizeof(struct gpiod_line_bulk));
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>","(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) bulkPtr);
}

JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1bulk_1add
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jlong linePtr) {
    gpiod_line_bulk_add((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, (struct gpiod_line*) (uintptr_t) linePtr);
}

JNIEXPORT jlong JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1bulk_1get_1line
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jint offset) {
    return (jlong) (uintptr_t) gpiod_line_bulk_get_line((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, offset);
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1bulk_1num_1lines
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr) {
    return gpiod_line_bulk_num_lines((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_offset
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1offset
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_offset((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_name
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1name
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    const char* c_name = gpiod_line_name((struct gpiod_line*) (uintptr_t) linePtr);
    return (*env)->NewStringUTF(env, c_name);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    gpiod_line_consumer
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1line_1consumer
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    const char* c_name = gpiod_line_consumer((struct gpiod_line*) (uintptr_t) linePtr);
    return (*env)->NewStringUTF(env, c_name);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_direction
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1direction
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_direction((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_active_state
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1active_1state
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_active_state((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_bias
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1bias
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_bias((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_is_used
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1is_1used
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_is_used((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_is_open_drain
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1is_1open_1drain
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_is_open_drain((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_is_open_source
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1is_1open_1source
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_is_open_source((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_update
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1update
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_update((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request
 * Signature: (JJI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request
  (JNIEnv* env, jclass javaClass, jlong linePtr, jlong requestConfigPtr, jint defaultVal) {
    return gpiod_line_request((struct gpiod_line*) (uintptr_t) linePtr, (struct gpiod_line_request_config*) (uintptr_t) linePtr, defaultVal);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_input
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1input
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_input((struct gpiod_line*) (uintptr_t) linePtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_output
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1output
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer, jint defaultVal) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_output((struct gpiod_line*) (uintptr_t) linePtr, c_consumer, defaultVal);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_rising_edge_events
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1rising_1edge_1events
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_rising_edge_events((struct gpiod_line*) (uintptr_t) linePtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_falling_edge_events
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1falling_1edge_1events
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_falling_edge_events((struct gpiod_line*) (uintptr_t) linePtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_both_edges_events
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1both_1edges_1events
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_both_edges_events((struct gpiod_line*) (uintptr_t) linePtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_input_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1input_1flags
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_input_flags((struct gpiod_line*) (uintptr_t) linePtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_output_flags
 * Signature: (JLjava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1output_1flags
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer, jint flags, jint defaultVal) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_output_flags((struct gpiod_line*) (uintptr_t) linePtr, c_consumer, flags, defaultVal);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_rising_edge_events_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1rising_1edge_1events_1flags
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_rising_edge_events_flags((struct gpiod_line*) (uintptr_t) linePtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_falling_edge_events_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1falling_1edge_1events_1flags
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_falling_edge_events_flags((struct gpiod_line*) (uintptr_t) linePtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_both_edges_events_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1both_1edges_1events_1flags
  (JNIEnv* env, jclass javaClass, jlong linePtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_both_edges_events_flags((struct gpiod_line*) (uintptr_t) linePtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk
 * Signature: (JJ[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jlong requestConfigPtr, jintArray defaultVals) {
    jint* c_defaultVals = (*env)->GetIntArrayElements(env, defaultVals, 0);
    int result = gpiod_line_request_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, (struct gpiod_line_request_config*) (uintptr_t) requestConfigPtr, c_defaultVals);
    (*env)->ReleaseIntArrayElements(env, defaultVals, c_defaultVals, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_input
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1input
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_input((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_output
 * Signature: (JLjava/lang/String;[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1output
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer, jintArray defaultVals) {
    jint* c_defaultVals = (*env)->GetIntArrayElements(env, defaultVals, 0);
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_output((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer, c_defaultVals);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    (*env)->ReleaseIntArrayElements(env, defaultVals, c_defaultVals, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_rising_edge_events
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1rising_1edge_1events
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_rising_edge_events((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_falling_edge_events
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1falling_1edge_1events
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_falling_edge_events((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_both_edges_events
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1both_1edges_1events
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_both_edges_events((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_input_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1input_1flags
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_input_flags((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_output_flags
 * Signature: (JLjava/lang/String;I[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1output_1flags
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer, jint flags, jintArray defaultVals) {
    jint* c_defaultVals = (*env)->GetIntArrayElements(env, defaultVals, 0);
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_output_flags((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer, flags, c_defaultVals);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    (*env)->ReleaseIntArrayElements(env, defaultVals, c_defaultVals, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_rising_edge_events_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1rising_1edge_1events_1flags
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_rising_edge_events_flags((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_falling_edge_events_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1falling_1edge_1events_1flags
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_falling_edge_events_flags((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_request_bulk_both_edges_events_flags
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1request_1bulk_1both_1edges_1events_1flags
  (JNIEnv* env, jclass javaClass, jlong bulkPtr, jstring consumer, jint flags) {
    const char* c_consumer = (*env)->GetStringUTFChars(env, consumer, NULL);
    int result = gpiod_line_request_bulk_both_edges_events_flags((struct gpiod_line_bulk*) (uintptr_t) bulkPtr, c_consumer, flags);
    (*env)->ReleaseStringUTFChars(env, consumer, c_consumer);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_release
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1release
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    gpiod_line_release((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_release_bulk
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1release_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr) {
    gpiod_line_release_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_is_requested
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1is_1requested
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_is_requested((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_is_free
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1is_1free
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_is_free((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_get_value
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1get_1value
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_get_value((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_get_value_bulk
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1get_1value_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jintArray values) {
    jint* c_values = (*env)->GetIntArrayElements(env, values, 0);
    int result = gpiod_line_get_value_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, c_values);
    (*env)->ReleaseIntArrayElements(env, values, c_values, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_value
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1value
  (JNIEnv* env, jclass javaClass, jlong linePtr, jint value) {
    return gpiod_line_set_value((struct gpiod_line*) (uintptr_t) linePtr, value);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_value_bulk
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1value_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jintArray values) {
    jint* c_values = (*env)->GetIntArrayElements(env, values, 0);
    int result = gpiod_line_set_value_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, c_values);
    (*env)->ReleaseIntArrayElements(env, values, c_values, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_config
 * Signature: (JIII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1config
  (JNIEnv* env, jclass javaClass, jlong linePtr, jint direction, jint flags, jint value) {
    return gpiod_line_set_config((struct gpiod_line*) (uintptr_t) linePtr, direction, flags, value);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_config_bulk
 * Signature: (JII[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1config_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jint direction, jint flags, jintArray values) {
    jint* c_values = (*env)->GetIntArrayElements(env, values, 0);
    int result = gpiod_line_set_config_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, direction, flags, c_values);
    (*env)->ReleaseIntArrayElements(env, values, c_values, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_flags
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1flags
  (JNIEnv* env, jclass javaClass, jlong linePtr, jint flags) {
    return gpiod_line_set_flags((struct gpiod_line*) (uintptr_t) linePtr, flags);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_flags_bulk
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1flags_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jint flags) {
    return gpiod_line_set_flags_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, flags);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_direction_input
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1direction_1input
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return gpiod_line_set_direction_input((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_direction_input_bulk
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1direction_1input_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr) {
    return gpiod_line_set_direction_input_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_direction_output
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1direction_1output
  (JNIEnv* env, jclass javaClass, jlong linePtr, jint value) {
    return gpiod_line_set_direction_output((struct gpiod_line*) (uintptr_t) linePtr, value);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_set_direction_output_bulk
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1set_1direction_1output_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jintArray values) {
    jint* c_values = (*env)->GetIntArrayElements(env, values, 0);
    int result = gpiod_line_set_direction_output_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, c_values);
    (*env)->ReleaseIntArrayElements(env, values, c_values, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_wait
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1wait
  (JNIEnv* env, jclass javaClass, jlong linePtr, jlong timeoutNs) {
    struct timespec timeout;
    timeout.tv_sec = timeoutNs / 1000000000;
    timeout.tv_nsec = timeoutNs % 1000000000;
    return gpiod_line_event_wait((struct gpiod_line*) (uintptr_t) linePtr, &timeout);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_wait_bulk
 * Signature: (JJJ)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1wait_1bulk
  (JNIEnv* env, jclass javaClass, jlong lineBulkPtr, jlong timeoutNs, jlong eventBulkPtr) {
    struct timespec timeout;
    timeout.tv_sec = timeoutNs / 1000000000;
    timeout.tv_nsec = timeoutNs % 1000000000;
    int result = gpiod_line_event_wait_bulk((struct gpiod_line_bulk*) (uintptr_t) lineBulkPtr, &timeout, (struct gpiod_line_bulk*) (uintptr_t) eventBulkPtr);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_read
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1read
  (JNIEnv* env, jclass javaClass, jlong linePtr, jlong eventPtr) {
    return gpiod_line_event_read((struct gpiod_line*) (uintptr_t) linePtr, (struct gpiod_line_event*) (uintptr_t) eventPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_read_multiple
 * Signature: (J[JI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1read_1multiple
  (JNIEnv* env, jclass javaClass, jlong linePtr, jlongArray events, jint numEvents) {
    jlong* c_events = (*env)->GetLongArrayElements(env, events, 0);
    int result = gpiod_line_event_read_multiple((struct gpiod_line*) (uintptr_t) linePtr, (struct gpiod_line_event*) (uintptr_t) c_events, numEvents);
    (*env)->ReleaseLongArrayElements(env, events, c_events, 0);
    return result;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_get
 * Signature: (Ljava/lang/String;I)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1get
  (JNIEnv* env, jclass javaClass, jstring device, jint offset) {
    const char* c_device = (*env)->GetStringUTFChars(env, device, NULL);
    struct gpiod_line* found = gpiod_line_get(c_device, offset);
    (*env)->ReleaseStringUTFChars(env, device, c_device);

    if(found == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) found);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    gpiod_line_find
 * Signature: (Ljava/lang/String;)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1line_1find
  (JNIEnv* env, jclass javaClass, jstring name) {
    const char* c_name = (*env)->GetStringUTFChars(env, name, NULL);
    struct gpiod_line* found = gpiod_line_find(c_name);
    (*env)->ReleaseStringUTFChars(env, name, c_name);

    if(found == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) found);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_close_chip
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1close_1chip
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    gpiod_line_close_chip((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    gpiod_line_get_chip
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1line_1get_1chip
  (JNIEnv* env, jclass javaClass, jlong linePtr) {
    return (jlong) (uintptr_t) gpiod_line_get_chip((struct gpiod_line*) (uintptr_t) linePtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    gpiod_chip_iter_new
 * Signature: ()Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1chip_1iter_1new
  (JNIEnv* env, jclass javaClass) {
    struct gpiod_chip_iter* iter = gpiod_chip_iter_new();

    if(iter == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) iter);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_chip_iter_free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1iter_1free
  (JNIEnv* env, jclass javaClass, jlong chipIterPtr) {
    gpiod_chip_iter_free((struct gpiod_chip_iter*) (uintptr_t) chipIterPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_chip_iter_free_noclose
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1iter_1free_1noclose
  (JNIEnv* env, jclass javaClass, jlong chipIterPtr) {
    gpiod_chip_iter_free_noclose((struct gpiod_chip_iter*) (uintptr_t) chipIterPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_chip_iter_next
 * Signature: (J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1iter_1next
  (JNIEnv* env, jclass javaClass, jlong chipIterPtr) {
    struct gpiod_chip* chip = gpiod_chip_iter_next((struct gpiod_chip_iter*) (uintptr_t) chipIterPtr);

    if(chip == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) chip);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_chip_iter_next_noclose
 * Signature: (J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1iter_1next_1noclose
  (JNIEnv* env, jclass javaClass, jlong chipIterPtr) {
    struct gpiod_chip* chip = gpiod_chip_iter_next_noclose((struct gpiod_chip_iter*) (uintptr_t) chipIterPtr);

    if(chip == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) chip);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    gpiod_line_iter_new
 * Signature: (J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1line_1iter_1new
  (JNIEnv* env, jclass javaClass, jlong lineIterPtr) {
    struct gpiod_line_iter* iter = gpiod_line_iter_new((struct gpiod_chip*) (uintptr_t) lineIterPtr);

    if(iter == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) iter);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    gpiod_line_iter_free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1line_1iter_1free
  (JNIEnv* env, jclass javaClass, jlong lineIterPtr) {
    gpiod_line_iter_free((struct gpiod_line_iter*) (uintptr_t) lineIterPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    gpiod_line_iter_next
 * Signature: (J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_gpiod_1line_1iter_1next
  (JNIEnv* env, jclass javaClass, jlong lineIterPtr) {
    struct gpiod_line* line = gpiod_line_iter_next((struct gpiod_line_iter*) (uintptr_t) lineIterPtr);

    if(line == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>", "(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) line);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_get_timespec
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1get_1timespec
  (JNIEnv* env, jclass javaClass, jlong eventPtr) {
    struct timespec ts = ((struct gpiod_line_event*) (uintptr_t) eventPtr)->ts;
    jlong tsNs = ts.tv_nsec;
    tsNs += ts.tv_sec * 1000000000;
    return tsNs;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_get_type
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1get_1type
  (JNIEnv* env, jclass javaClass, jlong eventPtr) {
    return ((struct gpiod_line_event*) (uintptr_t) eventPtr)->event_type;
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_new
 * Signature: ()Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1new
  (JNIEnv* env, jclass javaClass) {
    struct gpiod_line_event* eventPtr = (struct gpiod_line_event*) malloc(sizeof(struct gpiod_line_event));
    if(eventPtr == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>","(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) (uintptr_t) eventPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_line_event_free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1line_1event_1free
  (JNIEnv* env, jclass javaClass, jlong eventPtr) {
    free((struct gpiod_line_event*) (uintptr_t) eventPtr);
}

/*
 * Class:     com_pi4j_library_gpiod_internal_GpioD
 * Method:    c_gpiod_version_string
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1version_1string
  (JNIEnv* env, jclass javaClass) {
    return (*env)->NewStringUTF(env, gpiod_version_string());
}