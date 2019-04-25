#include <jni.h>
#include <string>
#include "crypt.cpp"


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_zz_cpptest_MainActivity_convert(
        JNIEnv* env,
        jobject /* this */,
        jstring filename) {
    const char *filename_in = env->GetStringUTFChars(filename, NULL);
    auto filename_out = convertName(filename_in);
    return convert(filename_in,filename_out);
}
