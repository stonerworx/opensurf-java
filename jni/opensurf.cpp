/***********************************************************
*  --- OpenSURF JNI Wrapper ---                            *
*                                                          *
*  Simple Wrapper for C. Evans OpenSURF to detect and      *
*  compute SURF features in Java.                          *
*                                                          *
*  OpenSURF:                                               *
*  C. Evans, Research Into Robust Visual Features,         *
*  MSc University of Bristol, 2008.                        *
*                                                          *
*  David Steiner <david@stonerworx.com>                    *
*                                                          *
************************************************************/

#include <opencv/cv.h>

#include "opensurf.h"
#include "ipoint.h"
#include "surflib.h"

JNIEXPORT void JNICALL Java_com_multicorn_opensurf_Opensurf_surflib_1surfDet
  (JNIEnv *, jclass, jlong imageMatAddr, jlong keypointMatAddr, jint octaves, 
    jint intervals, jint init_sample, jfloat thres)
{

  cv::Mat* imageMat = (cv::Mat*)imageMatAddr;
  cv::Mat* keypointMat = (cv::Mat*)keypointMatAddr;
  IplImage img = *imageMat;
  IpVec ipts;

  surfDet(&img, ipts, octaves, intervals, init_sample, thres);

  std::vector<cv::KeyPoint> keypoints;
  for (int i = 0; i < ipts.size(); i++) {
    Ipoint ipoint = ipts[i];
    cv::KeyPoint* keypoint = new cv::KeyPoint(ipoint.x, ipoint.y, ipoint.scale, ipoint.orientation);
    keypoints.push_back(*keypoint);
  }

  vector_KeyPoint_to_Mat(keypoints, *keypointMat);
}

JNIEXPORT void JNICALL Java_com_multicorn_opensurf_Opensurf_surflib_1surfDes
  (JNIEnv *, jclass, jlong imageMatAddr, jlong keypointMatAddr, jlong descriptorsAddr, jboolean upright)
{

  cv::Mat* imageMat = (cv::Mat*)imageMatAddr;
  cv::Mat* keypointMat = (cv::Mat*)keypointMatAddr;
  cv::Mat* descriptorMat = (cv::Mat*)descriptorsAddr;
  IplImage img = *imageMat;

  std::vector<cv::KeyPoint> keypoints;
  Mat_to_vector_KeyPoint(*keypointMat, keypoints);

  IpVec ipts;

  for (int i = 0; i < keypoints.size(); i++) {
    cv::KeyPoint keypoint = keypoints[i];
    Ipoint* ipoint = new Ipoint();
    ipoint->x = keypoint.pt.x;
    ipoint->y = keypoint.pt.y;
    ipoint->scale = keypoint.size;
    ipoint->orientation = keypoint.angle;
    ipts.push_back(*ipoint);
  }

  surfDes(&img, ipts, upright);

  cv::Mat* descriptors = new cv::Mat(ipts.size(), 64, cv::DataType<float>::type);
  for (int i = 0; i < ipts.size(); i++) {
    Ipoint ipoint = ipts[i];
    for (int j = 0; j < 64; j++) {
      descriptors->at<float>(i, j) = ipoint.descriptor[j];
    }
  }

  *descriptorMat = *descriptors;
}

void Mat_to_vector_KeyPoint(cv::Mat& mat, std::vector<cv::KeyPoint>& v_kp) {
  //source: http://stackoverflow.com/questions/14898540/converting-mat-to-keypoint

  v_kp.clear();
  for(int i=0; i<mat.rows; i++) {
    cv::Vec<float, 7> v = mat.at< cv::Vec<float, 7> >(i, 0);
    cv::KeyPoint kp(v[0], v[1], v[2], v[3], v[4], (int)v[5], (int)v[6]);
    v_kp.push_back(kp);
  }
  return;
}


void vector_KeyPoint_to_Mat(std::vector<cv::KeyPoint>& v_kp, cv::Mat& mat) {
  //source: http://stackoverflow.com/questions/14898540/converting-mat-to-keypoint

  int count = (int)v_kp.size();
  mat.create(count, 1, CV_32FC(7));
  for(int i=0; i<count; i++) {
    cv::KeyPoint kp = v_kp[i];
    mat.at< cv::Vec<float, 7> >(i, 0) = cv::Vec<float, 7>(kp.pt.x, kp.pt.y, kp.size, kp.angle, kp.response, (float)kp.octave, (float)kp.class_id);
  }
}
