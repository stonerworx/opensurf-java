package com.multicorn.opensurf;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

/**
 * Class Opensurf, created by David on 30.05.15.
 *
 * Simple JNI Wrapper for Chris Evans OpenSURF.
 *    C. Evans, Research Into Robust Visual Features,
 *    MSc University of Bristol, 2008.
 *
 * This library is distributed under the GNU GPL.
 *
 * Supports detection of SURF keypoints and computation of descriptors in images.
 *
 * @author David Steiner <david@stonerworx.com>
 */
public class Opensurf {

  /**
   * Detects SURF keypoints in an image.
   *
   * @param imageMat
   * @param octaves
   * @param intervals
   * @param init_sample
   * @param thres
   * @return MatOfKeyPoints with keypoints detected in the image
   */
  public static MatOfKeyPoint detect(Mat imageMat, int octaves, int intervals,
                                     int init_sample, float thres) {

    MatOfKeyPoint keyPoints = new MatOfKeyPoint();

    surflib_surfDet(imageMat.getNativeObjAddr(), keyPoints.getNativeObjAddr(),
                    octaves, intervals, init_sample, thres);

    return keyPoints;
  }

  /**
   * Computes SURF descriptors of an image given the previously detected keypoints.
   *
   * @param imageMat
   * @param keypoints
   * @param upright
   * @return Mat with all descriptors
   */
  public static Mat describe(Mat imageMat, MatOfKeyPoint keypoints, boolean upright) {

    Mat descriptors = new Mat();

    surflib_surfDes(imageMat.getNativeObjAddr(), keypoints.getNativeObjAddr(),
                    descriptors.getNativeObjAddr(), upright);

    return descriptors;
  }

  /**
   * Populates given MatOfKeyPoint and Mat with Keypoints and Descriptors
   *
   * @param imageMat
   * @param keyPoints
   * @param descriptors
   * @param octaves
   * @param intervals
   * @param init_sample
   * @param thres
   * @param upright
   */
  public static void detectDescribe(Mat imageMat, MatOfKeyPoint keyPoints, Mat descriptors,
                                    int octaves, int intervals, int init_sample, float thres,
                                    boolean upright) {

    surflib_surfDetDes(imageMat.getNativeObjAddr(), keyPoints.getNativeObjAddr(),
                       descriptors.getNativeObjAddr(), octaves, intervals, init_sample, thres,
                       upright);
  }

  public static native void surflib_surfDet(long imageMatAddr, long keypointsAddr,
                                            int octaves, int intervals,
                                            int init_sample, float thres);

  public static native void surflib_surfDes(long imageMatAddr, long keypointsAddr,
                                            long descriptorsAddr, boolean upright);

  public static native void surflib_surfDetDes(long imageMatAddr, long keypointsAddr,
                                               long descriptorsAddr, int octaves, int intervals,
                                               int init_sample, float thres, boolean upright);
}
