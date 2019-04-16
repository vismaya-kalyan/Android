package com.example.vismaya.cvcamera;

/**
 * Created by Vismaya on 21-Dec-17.
 */


        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.SurfaceView;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;

        import org.opencv.android.JavaCameraView;
        import org.opencv.android.BaseLoaderCallback;
        import org.opencv.android.CameraBridgeViewBase;
        import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
        import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
        import org.opencv.android.LoaderCallbackInterface;
        import org.opencv.android.OpenCVLoader;
        import org.opencv.core.Core;
        import org.opencv.core.CvType;
        import org.opencv.core.Mat;
        import org.opencv.core.MatOfPoint;
        import org.opencv.core.MatOfPoint2f;
        import org.opencv.core.Scalar;
        import org.opencv.core.Size;
        import org.opencv.imgproc.Imgproc;

        import java.util.ArrayList;
        import java.util.List;

// OpenCV Classes

public class MainActivity_show_camera extends AppCompatActivity implements CvCameraViewListener2 {

    // Used for logging success or failure messages
    private static final String TAG = "OCVSample::Activity";

    // Loads camera view of OpenCV for us to use. This lets us see using OpenCV
    private CameraBridgeViewBase mOpenCvCameraView;

    // Used in Camera selection from menu (when implemented)
    private boolean              mIsJavaCamera = true;
    private MenuItem             mItemSwitchCamera = null;

    // These variables are used (at the moment) to fix camera orientation from 270degree to 0degree
    Mat mRgba;
    Mat mRgbaF;
    Mat mRgbaT;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public MainActivity_show_camera() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.show_camera);

        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.show_camera_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            //ask for authorisation
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 50);

    }



    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    Mat imgCanny, hierarchy;
    List<MatOfPoint> contours;
    MatOfPoint temp_contour;


    public void onCameraViewStarted(int width, int height) {

        mRgba = new Mat(height, width, CvType.CV_8UC4);
        imgCanny = new Mat(height, width, CvType.CV_8UC1);

        hierarchy = new Mat();
        contours = new ArrayList<MatOfPoint>();

        mRgbaF = new Mat(height, width, CvType.CV_8UC4);
        mRgbaT = new Mat(width, width, CvType.CV_8UC4);
    }

    public void onCameraViewStopped() {
        mRgba.release();
        imgCanny.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        // TODO Auto-generated method stub
       // Size size = new Size(mRgba.width(),mRgba.height());

        mRgba = inputFrame.rgba();
        Imgproc.Canny(mRgba,imgCanny,127,255,3,true);
        Imgproc.findContours(imgCanny,contours, hierarchy, Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_NONE);
//      temp_contour = contours.get(0);
        return mRgba; // This function must return
    }

}