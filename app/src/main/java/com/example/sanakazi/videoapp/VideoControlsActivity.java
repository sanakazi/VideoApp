package com.example.sanakazi.videoapp;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import java.io.IOException;

import static com.example.sanakazi.videoapp.R.id.rlPlayerView;

public class VideoControlsActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = VideoControlsActivity.class.getSimpleName();
    RelativeLayout rlBottomControls,frameContainer,rlPlayerView;
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    public static SurfaceHolder mSurfaceHolder;
    private VideoStream videoStream_object;
    private ImageView ivPlay;
    public static boolean isPlaying = true;
    public static SeekBar seekBarVideo;
    public static TextView tvDuration, tvStartTime;
    public static int store_seek_progress = 0;


    //  private static final String VIDEO_PATH="http://classicmusic-9aaa.kxcdn.com/video/Tu%20hi%20hai%20aashiqui%20orignal%20song%20from%20movie.mp4";
    // private static final String VIDEO_PATH = "https://inducesmile.com/wp-content/uploads/2016/05/small.mp4";
    private static final String VIDEO_PATH = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private static int bottomPlayerVisibility = 1;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_controls);
        Log.w(TAG, "onCreate() of activity");
        initialize();
        click_events();

    }


    private void initialize() {
        rlPlayerView= (RelativeLayout) findViewById(R.id.rlPlayerView);
        frameContainer = (RelativeLayout) findViewById(R.id.frameContainer);
        rlBottomControls = (RelativeLayout) findViewById(R.id.rlBottomControls);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        ivPlay = (ImageView) findViewById(R.id.ivPlay);
        tvDuration = (TextView) findViewById(R.id.tvDuration);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        seekBarVideo = (SeekBar) findViewById(R.id.seekBar);
        tvStartTime.setText("00:00:00");
        tvDuration.setText("00:00:00");
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(VideoControlsActivity.this);

    }


    private void click_events() {
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                }
            }
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    videoStream_object.pause();
                    ivPlay.setImageResource(R.mipmap.stop_video);

                } else {
                    // videoStream_object.play(VIDEO_PATH);
                    ivPlay.setImageResource(R.mipmap.play_video);
                    videoStream_object.playerResume();

                }
            }
        });

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.w(TAG, "surface surfaceCreated" + ", isPlaying = " + isPlaying);
        if (videoStream_object == null) {
            if (isPlaying) {
                videoStream_object = new VideoStream(VideoControlsActivity.this);
                videoStream_object.play(VIDEO_PATH);
                videoStream_object.setSeekBar(seekBarVideo);

            }
        }
        else
        {
            videoStream_object.playerForSurfaceView();
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.w(TAG, "surface surfaceChanged");


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.w(TAG, "surface surfaceChanged");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause() of activity");
        ivPlay.setImageResource(R.mipmap.stop_video);
        videoStream_object.pause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy() of activity");
        videoStream_object.releaseMediaPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume() of activity" + ",isPlaying = " + isPlaying);
        if (isPlaying) {
            ivPlay.setImageResource(R.mipmap.play_video);
        } else {
            ivPlay.setImageResource(R.mipmap.stop_video);
            // videoStream_object.playerResume();
        }


    }

 public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
          Log.w(TAG,"Configuration is " + " landscape" + newConfig.orientation);


            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            rlBottomControls.setVisibility(View.VISIBLE);

           // player.setUpVideoDimensions();
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            frameContainer.setLayoutParams(params);
            RelativeLayout.LayoutParams paramsBottom = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, rlPlayerView.getId());
            rlBottomControls.setLayoutParams(paramsBottom);


            //this code will return access to automatic sensor again for orientation
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                }
            }, 2000);








        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.w(TAG,"Configuration is " + " portrait" );
        }
    }


}

