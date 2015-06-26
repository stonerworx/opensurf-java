package com.multicorn.opensurf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.highgui.Highgui;

/**
 * Class OpensurfTest, created by David on 31.05.15.
 *
 * @author David Steiner <david@stonerworx.com>
 */
public class OpensurfTest {

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    System.load("/home/user/app/libs/native/libopensurf_java.so");
  }

  @Test
  public void testKeypointDetection() {

    MatOfKeyPoint keyPoints = Opensurf.detect(getImage(), 5, 4, 2, 0.0004f);

    assertTrue("keypoint mat should have rows", keyPoints.rows() > 0);
    assertEquals("keypoint mat should one col", 1, keyPoints.cols());
  }

  @Test
  public void testDescriptorComputation() {

    Mat imageMat = getImage();

    MatOfKeyPoint keyPoints = Opensurf.detect(imageMat, 5, 4, 2, 0.0004f);
    Mat descriptors = Opensurf.compute(imageMat, keyPoints, true);

    assertTrue("keyoint and descriptor rows should match",
               keyPoints.rows() == descriptors.rows());
    assertEquals("descriptor should have 64 cols", 64, descriptors.cols());
  }

  private Mat getImage() {
    return Highgui.imread(getClass().getResource("/test.jpg").getPath());
  }

}
