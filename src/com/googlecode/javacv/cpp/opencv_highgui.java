/*
 * Copyright (C) 2011 Samuel Audet
 *
 * This file is part of JavaCV.
 *
 * JavaCV is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version (subject to the "Classpath" exception
 * as provided in the LICENSE.txt file that accompanied this code).
 *
 * JavaCV is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaCV.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * This file is based on information found in highgui_c.h
 * of OpenCV 2.2, which are covered by the following copyright notice:
 *
 *                          License Agreement
 *                For Open Source Computer Vision Library
 *
 * Copyright (C) 2000, Intel Corporation, all rights reserved.
 * Third party copyrights are property of their respective owners.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * Redistribution's of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *   * Redistribution's in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *   * The name of the copyright holders may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * This software is provided by the copyright holders and contributors "as is" and
 * any express or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are disclaimed.
 * In no event shall the Intel Corporation or contributors be liable for any direct,
 * indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services;
 * loss of use, data, or profits; or business interruption) however caused
 * and on any theory of liability, whether in contract, strict liability,
 * or tort (including negligence or otherwise) arising in any way out of
 * the use of this software, even if advised of the possibility of such damage.
 *
 */

package com.googlecode.javacv.cpp;

import com.googlecode.javacpp.FunctionPointer;
import com.googlecode.javacpp.Pointer;
import com.googlecode.javacpp.PointerPointer;
import com.googlecode.javacpp.annotation.ByPtrPtr;
import com.googlecode.javacpp.annotation.ByVal;
import com.googlecode.javacpp.annotation.Cast;
import com.googlecode.javacpp.annotation.Opaque;
import com.googlecode.javacpp.annotation.Platform;
import com.googlecode.javacpp.annotation.Properties;

import static com.googlecode.javacpp.Loader.*;
import static com.googlecode.javacv.cpp.opencv_core.*;

/**
 *
 * @author saudet
 */
@Properties({
    @Platform(include="<opencv2/highgui/highgui_c.h>", includepath=genericIncludepath,
        linkpath=genericLinkpath,       link="opencv_highgui"),
    @Platform(value="windows", includepath=windowsIncludepath, linkpath=windowsLinkpath,
        preloadpath=windowsPreloadpath, link="opencv_highgui220", preload="opencv_ffmpeg220"),
    @Platform(value="android", includepath=androidIncludepath, linkpath=androidLinkpath) })
public class opencv_highgui {
    static {
        try {
            load(avformat.class); load(swscale.class);
        } catch (Throwable t) {
            // oh well, at least we tried to preload the correct version of FFmpeg...
        } finally {
            load(opencv_imgproc.class); load();
        }
    }

    public static final int
            CV_FONT_LIGHT          = 25,
            CV_FONT_NORMAL         = 50,
            CV_FONT_DEMIBOLD       = 63,
            CV_FONT_BOLD           = 75,
            CV_FONT_BLACK          = 87,

            CV_STYLE_NORMAL        = 0,
            CV_STYLE_ITALIC        = 1,
            CV_STYLE_OBLIQUE       = 2;

    @Platform("linux")
    public static native @ByVal CvFont cvFontQt(String nameFont, int pointSize/*=-1*/,
            @ByVal CvScalar color/*=cvScalarAll(0)*/, int weight/*=CV_FONT_NORMAL*/,
            int style/*=CV_STYLE_NORMAL*/, int spacing/*=0*/);

    @Platform("linux")
    public static native void cvAddText(CvArr img, String text, @ByVal CvPoint org, CvFont arg2);

    @Platform("linux")
    public static native void cvDisplayOverlay(String name, String text, int delayms);
    @Platform("linux")
    public static native void cvDisplayStatusBar(String name, String text, int delayms);

    @Platform("linux")
    public static class CvOpenGLCallback extends FunctionPointer {
        static { load(); }
        public    CvOpenGLCallback(Pointer p) { super(p); }
        protected CvOpenGLCallback() { allocate(); }
        protected final native void allocate();
        public native void call(Pointer userdata);
    }
    @Platform("linux")
    public static native void cvCreateOpenGLCallback(String window_name, CvOpenGLCallback callbackOpenGL,
            Pointer userdata/*=null*/, double angle/*=-1*/, double zmin/*=-1*/, double zmax/*=-1*/);

    @Platform("linux")
    public static native void cvSaveWindowParameters(String name);
    @Platform("linux")
    public static native void cvLoadWindowParameters(String name);
    @Platform("linux")
    public static class Pt2Func extends FunctionPointer {
        static { load(); }
        public    Pt2Func(Pointer p) { super(p); }
        protected Pt2Func() { allocate(); }
        protected final native void allocate();
        public native int call(int argc, @Cast("char**") PointerPointer argv);
    }
    @Platform("linux")
    public static native int cvStartLoop(Pt2Func pt2Func, int argc, @Cast("char**") PointerPointer argv);
    @Platform("linux")
    public static native void cvStopLoop();

    @Platform("linux")
    public static class CvButtonCallback extends FunctionPointer {
        static { load(); }
        public    CvButtonCallback(Pointer p) { super(p); }
        protected CvButtonCallback() { allocate(); }
        protected final native void allocate();
        public native void call(int state, Pointer userdata);
    }
    public static int CV_PUSH_BUTTON = 0, CV_CHECKBOX = 1, CV_RADIOBOX = 2;
    @Platform("linux")
    public static native int cvCreateButton(String button_name/*=null*/, CvButtonCallback on_change/*=null*/,
            Pointer userdata/*=null*/, int button_type/*=CV_PUSH_BUTTON*/, int initial_button_state/*=0*/);


    public static native int cvInitSystem(int argc, @Cast("char**") PointerPointer argv);
    public static native int cvStartWindowThread();

    public static final int
	CV_WND_PROP_FULLSCREEN  = 0,
	CV_WND_PROP_AUTOSIZE    = 1,
	CV_WND_PROP_ASPECTRATIO = 2,

	CV_WINDOW_NORMAL        = 0x00000000,
	CV_WINDOW_AUTOSIZE 	= 0x00000001,

	CV_GUI_EXPANDED 	= 0x00000000,
	CV_GUI_NORMAL 		= 0x00000010,

	CV_WINDOW_FULLSCREEN    = 1,
	CV_WINDOW_FREERATIO	= 0x00000100,
	CV_WINDOW_KEEPRATIO     = 0x00000000;

    public static int cvNamedWindow(String name) { return cvNamedWindow(name, CV_WINDOW_AUTOSIZE); }
    public static native int cvNamedWindow(String name, int flags/*=CV_WINDOW_AUTOSIZE*/);

    public static native void cvSetWindowProperty(String name, int prop_id, double prop_value);
    public static native double cvGetWindowProperty(String name, int prop_id);

    public static native void cvShowImage(String name, CvArr image);

    public static native void cvResizeWindow(String name, int width, int height);
    public static native void cvMoveWindow(String name, int x, int y);

    public static native void cvDestroyWindow(String name);
    public static native void cvDestroyAllWindows();

    public static native Pointer cvGetWindowHandle(String name);
    public static native String cvGetWindowName(Pointer window_handle);


    public static class CvTrackbarCallback extends FunctionPointer {
        static { load(); }
        public    CvTrackbarCallback(Pointer p) { super(p); }
        protected CvTrackbarCallback() { allocate(); }
        protected final native void allocate();
        public native void call(int pos);
    }
    public static native int cvCreateTrackbar(String trackbar_name, String window_name,
            int[] value, int count, CvTrackbarCallback on_change/*=null*/);

    public static class CvTrackbarCallback2 extends FunctionPointer {
        static { load(); }
        public    CvTrackbarCallback2(Pointer p) { super(p); }
        protected CvTrackbarCallback2() { allocate(); }
        protected final native void allocate();
        public native void call(int pos, Pointer userdata);
    }
    public static native int cvCreateTrackbar2(String trackbar_name, String window_name,
            int[] value, int count, CvTrackbarCallback2 on_change, Pointer userdata/*=null*/);

    public static native int cvGetTrackbarPos(String trackbar_name, String window_name);
    public static native void cvSetTrackbarPos(String trackbar_name, String window_name, int pos);


    public static final int
            CV_EVENT_MOUSEMOVE     = 0,
            CV_EVENT_LBUTTONDOWN   = 1,
            CV_EVENT_RBUTTONDOWN   = 2,
            CV_EVENT_MBUTTONDOWN   = 3,
            CV_EVENT_LBUTTONUP     = 4,
            CV_EVENT_RBUTTONUP     = 5,
            CV_EVENT_MBUTTONUP     = 6,
            CV_EVENT_LBUTTONDBLCLK = 7,
            CV_EVENT_RBUTTONDBLCLK = 8,
            CV_EVENT_MBUTTONDBLCLK = 9,

            CV_EVENT_FLAG_LBUTTON  = 1,
            CV_EVENT_FLAG_RBUTTON  = 2,
            CV_EVENT_FLAG_MBUTTON  = 4,
            CV_EVENT_FLAG_CTRLKEY  = 8,
            CV_EVENT_FLAG_SHIFTKEY = 16,
            CV_EVENT_FLAG_ALTKEY   = 32;

    public static class CvMouseCallback extends FunctionPointer {
        static { load(); }
        public    CvMouseCallback(Pointer p) { super(p); }
        protected CvMouseCallback() { allocate(); }
        protected final native void allocate();
        public native void call(int event, int x, int y, int flags, Pointer param);
    }
    public static native void cvSetMouseCallback(String window_name,
            CvMouseCallback on_mouse, Pointer param/*=null*/);


    public static final int
            CV_LOAD_IMAGE_UNCHANGED  = -1,
            CV_LOAD_IMAGE_GRAYSCALE  = 0,
            CV_LOAD_IMAGE_COLOR      = 1,
            CV_LOAD_IMAGE_ANYDEPTH   = 2,
            CV_LOAD_IMAGE_ANYCOLOR   = 4;

    public static IplImage cvLoadImage(String filename) { return cvLoadImage(filename, CV_LOAD_IMAGE_COLOR); }
    public static native IplImage cvLoadImage(String filename, int iscolor/*=CV_LOAD_IMAGE_COLOR*/);
    public static CvMat cvLoadImageM(String filename)   { return cvLoadImageM(filename, CV_LOAD_IMAGE_COLOR); }
    public static native CvMat cvLoadImageM(String filename, int iscolor/*=CV_LOAD_IMAGE_COLOR*/);

    public static final int
            CV_IMWRITE_JPEG_QUALITY    = 1,
            CV_IMWRITE_PNG_COMPRESSION = 16,
            CV_IMWRITE_PXM_BINARY      = 32;

    public static int cvSaveImage(String filename, CvArr image) { return cvSaveImage(filename, image, null); }
    public static native int cvSaveImage(String filename, CvArr image, int[] params/*=null*/);

    public static IplImage cvDecodeImage(CvMat buf) { return cvDecodeImage(buf, CV_LOAD_IMAGE_COLOR); }
    public static native IplImage cvDecodeImage(CvMat buf, int iscolor/*=CV_LOAD_IMAGE_COLOR*/);
    public static CvMat cvDecodeImageM(CvMat buf)   { return cvDecodeImageM(buf, CV_LOAD_IMAGE_COLOR); }
    public static native CvMat cvDecodeImageM(CvMat buf, int iscolor/*=CV_LOAD_IMAGE_COLOR*/);
    public static CvMat cvEncodeImage(String ext, CvArr image) { return cvEncodeImage(ext, image, null); }
    public static native CvMat cvEncodeImage(String ext, CvArr image, int[] params/*=null*/);

    public static final int
            CV_CVTIMG_FLIP    = 1,
            CV_CVTIMG_SWAP_RB = 2;

    public static native void cvConvertImage(CvArr src, CvArr dst, int flags/*=0*/);

    public static int cvWaitKey() { return cvWaitKey(0); }
    public static native int cvWaitKey(int delay/*=0*/);


    @Opaque public static class CvCapture extends Pointer {
        static { load(); }
        public CvCapture() { }
        public CvCapture(Pointer p) { super(p); }
    }
    public static native CvCapture cvCreateFileCapture(String filename);

    public static final int
            CV_CAP_ANY     = 0,

            CV_CAP_MIL     = 100,

            CV_CAP_VFW     = 200,
            CV_CAP_V4L     = 200,
            CV_CAP_V4L2    = 200,

            CV_CAP_FIREWARE= 300,
            CV_CAP_FIREWIRE= 300,
            CV_CAP_IEEE1394= 300,
            CV_CAP_DC1394  = 300,
            CV_CAP_CMU1394 = 300,

            CV_CAP_STEREO  = 400,
            CV_CAP_TYZX    = 400,
            CV_TYZX_LEFT   = 400,
            CV_TYZX_RIGHT  = 401,
            CV_TYZX_COLOR  = 402,
            CV_TYZX_Z      = 403,

            CV_CAP_QT      = 500,

            CV_CAP_UNICAP  = 600,

            CV_CAP_DSHOW   = 700,

            CV_CAP_PVAPI   = 800;

    public static native CvCapture cvCreateCameraCapture(int index);
    public static native int cvGrabFrame(CvCapture capture);
    public static IplImage cvRetrieveFrame(CvCapture capture) { return cvRetrieveFrame(capture, 0); }
    public static native IplImage cvRetrieveFrame(CvCapture capture, int streamIdx/*=0*/);
    public static native IplImage cvQueryFrame(CvCapture capture);
    public static native void cvReleaseCapture(@ByPtrPtr CvCapture capture);

    public static final int
            CV_CAP_PROP_POS_MSEC      =  0,
            CV_CAP_PROP_POS_FRAMES    =  1,
            CV_CAP_PROP_POS_AVI_RATIO =  2,
            CV_CAP_PROP_FRAME_WIDTH   =  3,
            CV_CAP_PROP_FRAME_HEIGHT  =  4,
            CV_CAP_PROP_FPS           =  5,
            CV_CAP_PROP_FOURCC        =  6,
            CV_CAP_PROP_FRAME_COUNT   =  7,
            CV_CAP_PROP_FORMAT        =  8,
            CV_CAP_PROP_MODE          =  9,
            CV_CAP_PROP_BRIGHTNESS    = 10,
            CV_CAP_PROP_CONTRAST      = 11,
            CV_CAP_PROP_SATURATION    = 12,
            CV_CAP_PROP_HUE           = 13,
            CV_CAP_PROP_GAIN          = 14,
            CV_CAP_PROP_EXPOSURE      = 15,
            CV_CAP_PROP_CONVERT_RGB   = 16,
            CV_CAP_PROP_WHITE_BALANCE = 17,
            CV_CAP_PROP_RECTIFICATION = 18,
            CV_CAP_PROP_MONOCROME     = 19;

    public static native double cvGetCaptureProperty(CvCapture capture, int property_id);
    public static native int    cvSetCaptureProperty(CvCapture capture, int property_id, double value);
    public static native int    cvGetCaptureDomain(CvCapture capture);


    @Opaque public static class CvVideoWriter extends Pointer {
        static { load(); }
        public CvVideoWriter() { }
        public CvVideoWriter(Pointer p) { super(p); }
    }

    public static int CV_FOURCC(byte c1, byte c2, byte c3, byte c4) {
        return (c1&255) + ((c2&255)<<8) + ((c3&255)<<16) + ((c4&255)<<24);
    }
    public static int CV_FOURCC(char c1, char c2, char c3, char c4) {
        return CV_FOURCC((byte)c1, (byte)c2, (byte)c3, (byte)c4);
    }

    public static final int
        CV_FOURCC_PROMPT  = -1,
        CV_FOURCC_DEFAULT = CV_FOURCC('I', 'Y', 'U', 'V');

    public static native CvVideoWriter cvCreateVideoWriter(String filename, int fourcc,
            double fps, @ByVal CvSize frame_size, int is_color/*=1*/);
    public static native int cvWriteFrame(CvVideoWriter writer, IplImage image);
    public static native void cvReleaseVideoWriter(@ByPtrPtr CvVideoWriter writer);
}
