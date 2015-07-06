[![Circle CI](https://circleci.com/gh/stonerworx/opensurf-java.svg?style=svg)](https://circleci.com/gh/stonerworx/opensurf-java)

# opensurf-java
Simple JNI Wrapper for Chris Evans OpenSURF.<br />
*(C. Evans, Research Into Robust Visual Features, MSc University of Bristol, 2008.)*<br />
This library is distributed under the GNU GPL.

**Supports detection of SURF keypoints and computation of descriptors.**

## The easy way - use Docker

* Get [Docker](https://www.docker.com/) for your platform.

If you just want to use OpenSURF in Java you can get a pre-built image.
It includes a working copy of OpenSURF and OpenCV 2.4.11 with Oracle Java 8 and Gradle running
on Ubuntu Trusty.

* Pull it from the repo
  <pre>docker pull stonerworx/opensurf-java
  
* Or use it in your Dockerfile
  <pre>FROM stonerworx/opensurf-java
  
* The OpenSURF library is located in **/home/javalibs/opensurf-1.0.jar**
* The OpenCV library is located in **/home/javalibs/opencv-2411.jar**

If you'd like to contribute you can use the Dockerfile to run tests:

* Build the Dockerfile
  <pre>docker build -t opensurf-java .</pre>
  
* Run the test
  <pre>docker run opensurf-java gradle test</pre>

## The slightly more difficult way

Requires [OpenCV](http://opencv.org/) to be installed.

* Install [OpenCV](http://opencv.org/) *(I recommend using [MacPorts](https://www.macports.org))*
  <pre>port install opencv +java</pre>
  
* Copy **opencv-2411.jar** in */opt/local/share/OpenCV/java* to *libs/*

### Mac OS X
 
* Copy the **libopencv_java2411.dylib** file in */opt/local/share/OpenCV/java* to your *java.library.path*
 
* Build the native library *(Use the makefile.mac - You may need to edit some paths in the Makefile)*
  <pre>
  cd jni<br />
  make
  </pre>
 
* Copy the **libopensurf_java.dylib** file in *libs/native* to your *java.library.path*

* Build the project
  <pre>gradle build</pre>

* Add **opensurf-1.0.jar** as a dependency in your project

### Android (Android Studio)

Requires the [Android NDK](https://developer.android.com/tools/sdk/ndk/index.html) to build the libraries.

* Download the [OpenCV Android SDK](http://docs.opencv.org/doc/tutorials/introduction/android_binary_package/O4A_SDK.html) here: http://sourceforge.net/projects/opencvlibrary/files/opencv-android/

* Build the native libraries *(You need to edit **OPENCV_PATH** in Android.mk)*
  <pre>
  cd jni<br />
  ndk-build
  </pre>
  
* Create a **jniLibs** Folder in your Android project under *src/main*

* Copy the armeabi, armeabi-v7a, mips and x86 folders from *libs/native* to your **jniLibs** folder

* Build the project
  <pre>gradle build</pre>

* Add **opensurf-1.0.jar** as a dependency in your project

* See http://stackoverflow.com/a/27421494 for instructions on how to use OpenCV in Android Studio

## Usage

<pre>
import com.multicorn.opensurf.Opensurf;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.highgui.Highgui;

MatOfKeyPoint keyPoints;
Mat descriptors;

/*
 * detect, then describe
*/

Mat imageMat = Highgui.imread(getClass().getResource("/test.jpg").getPath());

keyPoints = Opensurf.detect(imageMat, 5, 4, 2, 0.0004f);

descriptors = Opensurf.describe(imageMat, keyPoints, true);

/*
 * detect & describe
*/

keyPoints = new MatOfKeyPoint();
descriptors = new Mat();

Opensurf.detectDescribe(imageMat, keyPoints, descriptors, 5, 4, 2, 0.0004f, true);

</pre>