
#include <gpiod.h>
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
    jclass cls = (*env)->FindClass(env,"java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env,cls,"<init>","(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) chip);
}

JNIEXPORT void JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1close
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    gpiod_chip_close((struct gpiod_chip*) chipPtr);
  }

JNIEXPORT jstring JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1name
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    const char* name = gpiod_chip_name((struct gpiod_chip*) chipPtr);
    jstring jStrName = (*env)->NewStringUTF(env, name);
    return jStrName;
  }

JNIEXPORT jstring JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1label
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    const char* label = gpiod_chip_label((struct gpiod_chip*) chipPtr);
    jstring jStrName = (*env)->NewStringUTF(env, label);
    return jStrName;
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1num_1lines
  (JNIEnv* env, jclass javaClass, jlong chipPtr) {
    unsigned int num_lines = gpiod_chip_num_lines((struct gpiod_chip*) chipPtr);
    return num_lines;
}

JNIEXPORT jobject JNICALL Java_com_pi4j_library_gpiod_internal_GpioD_c_1gpiod_1chip_1get_1line
  (JNIEnv* env, jclass javaClass, jlong chipPtr, jint offset) {
    struct gpiod_line* line;
    line = gpiod_chip_get_line((struct gpiod_chip*) chipPtr, offset);
    if(line == NULL) {
      return NULL;
    }
    jclass cls = (*env)->FindClass(env,"java/lang/Long");
    jmethodID longConstructor = (*env)->GetMethodID(env, cls, "<init>","(J)V");
    return (*env)->NewObject(env, cls, longConstructor, (jlong) line);
}