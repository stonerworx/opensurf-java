LOCAL_PATH  := $(call my-dir)

#path to opencv android sdk
OPENCV_PATH := /Users/dsteiner/dev/OpenCV-android-sdk/sdk/native/jni

include $(CLEAR_VARS)
OPENCV_INSTALL_MODULES := on
OPENCV_CAMERA_MODULES  := off
include $(OPENCV_PATH)/OpenCV.mk

LOCAL_C_INCLUDES :=       \
  $(LOCAL_PATH)       \
  $(OPENCV_PATH)/include

LOCAL_SRC_FILES :=        \
  utils.cpp          \
  surf.cpp          \
  ipoint.cpp          \
  integral.cpp          \
  fasthessian.cpp          \
  opensurf.cpp

LOCAL_MODULE := opensurf
LOCAL_CFLAGS := -Werror -O3 -ffast-math
LOCAL_LDLIBS := -llog -ldl

include $(BUILD_SHARED_LIBRARY)