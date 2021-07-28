#include <jni.h>

int grayScalePixel(int i);

extern "C"
JNIEXPORT void JNICALL
Java_hins_clay_test_MainActivity_nativeFilter(JNIEnv *env, jobject obj, jintArray src, jintArray dst, jint width, jint height) {
    int *in = env->GetIntArrayElements(src, NULL);
    int *out = env->GetIntArrayElements(dst, NULL);
    jsize lenghtSrc = env->GetArrayLength(dst);


    ///* // Solution Classic
    int i ;
    for (i=0; i<lenghtSrc; i++) {
        out[i] = grayScalePixel(in[i]) ;
    }

    env->ReleaseIntArrayElements(src, in, 0);
    env->ReleaseIntArrayElements(dst, out, 0);
}

int grayScalePixel(int pixel) {
    int a = (pixel >> 24)&0xff ;
    int r = (pixel >> 16) & 0xff ;
    int g = (pixel >> 8) & 0xff ;
    int b = pixel & 0xff ;

    int avg = (r + g + b)/3 ;
    return (a<<24) | (avg<<16) | (avg<<8) | avg ;
}