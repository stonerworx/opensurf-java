package com.multicorn.opensurf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.highgui.Highgui;

/**
 * Class OpensurfTest, created by David on 31.05.15.
 *
 * @author David Steiner <david@stonerworx.com>
 */
public class OpensurfTest {

  @Test
  public void testKeypointDetection() {
    Opensurf opensurf = new Opensurf();

    Mat imageMat = Highgui.imread(getClass().getResource("/test.jpg").getPath());

    MatOfKeyPoint keyPoints = opensurf.detect(imageMat, 5, 4, 2, 0.0004f);

    assertTrue("keypoint mat should have rows", keyPoints.rows() > 0);
    assertEquals("keypoint mat should one col", 1, keyPoints.cols());
  }

  @Test
  public void testDescriptorComputation() {
    Opensurf opensurf = new Opensurf();

    Mat imageMat = Highgui.imread(getClass().getResource("/test.jpg").getPath());

    MatOfKeyPoint keyPoints = opensurf.detect(imageMat, 5, 4, 2, 0.0004f);
    Mat descriptors = opensurf.compute(imageMat, keyPoints, true);

    assertTrue("keyoint and descriptor rows should match", keyPoints.rows() == descriptors.rows());
    assertEquals("descriptor should have 64 cols", 64, descriptors.cols());
  }

}
