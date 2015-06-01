package com.multicorn.opensurf;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

/**
 * Class Opensurf, created by David on 30.05.15.
 *
 * Simple JNI Wrapper for C. Evans OpenSURF.
 *    C. Evans, Research Into Robust Visual Features,
 *    MSc University of Bristol, 2008.
 *
 * Supports detection of keypoints and computation of descriptors.
 *
 * @author David Steiner <david@stonerworx.com>
 */
public class Opensurf {

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    System.loadLibrary("opensurf");
  }

  public MatOfKeyPoint detect(Mat imageMat, int octaves, int intervals,
                                     int init_sample, float thres) {

    MatOfKeyPoint keyPoints = new MatOfKeyPoint();

    surflib_surfDet(imageMat.getNativeObjAddr(), keyPoints.getNativeObjAddr(),
                    octaves, intervals, init_sample, thres);

    return keyPoints;
  }

  public Mat compute(Mat imageMat, MatOfKeyPoint keypoints, boolean upright) {

    Mat descriptors = new Mat();

    surflib_surfDes(imageMat.getNativeObjAddr(), keypoints.getNativeObjAddr(),
                    descriptors.getNativeObjAddr(), upright);

    return descriptors;
  }


  public static native void surflib_surfDet(long imageMatAddr, long keypointsAddr,
                                            int octaves, int intervals,
                                            int init_sample, float thres);

  public static native void surflib_surfDes(long imageMatAddr, long keypointsAddr,
                                            long descriptorsAddr, boolean upright);

}
