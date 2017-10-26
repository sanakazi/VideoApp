package com.example.sanakazi.videoapp;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.io.IOException;

public class BasicVideoActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private static final String TAG = BasicVideoActivity.class.getSimpleName();
    RelativeLayout rlBottomControls;
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;


    private static final String VIDEO_PATH="http://classicmusic-9521.kxcdn.com/video/Tu%20hi%20hai%20aashiqui%20orignal%20song%20from%20movie.mp4";
  // private static final String VIDEO_PATH="https://inducesmile.com/wp-content/uploads/2016/05/small.mp4";
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
        setContentView(R.layout.activity_basic_video);
        initialize();
        click_events();

    }


    private void initialize() {
        rlBottomControls = (RelativeLayout) findViewById(R.id.rlBottomControls);
        mSurfaceView = (SurfaceView)findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(BasicVideoActivity.this);
    }


    private void click_events() {
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    if (bottomPlayerVisibility == 1) {
                        rlBottomControls.setVisibility(View.INVISIBLE);
                        bottomPlayerVisibility=0;
                    } else {
                        rlBottomControls.setVisibility(View.VISIBLE);
                        bottomPlayerVisibility = 1;
                    }
                }
            }
        });

    }




    private void hideBottomPlayer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rlBottomControls.setVisibility(View.INVISIBLE);
            }
        }, SPLASH_TIME_OUT);

        bottomPlayerVisibility = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mMediaPlayer.setDataSource(VIDEO_PATH);
            mMediaPlayer.prepareAsync();
           // mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(BasicVideoActivity.this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


   /* public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
          Log.w(TAG,"Configuration is " + " landscape" + newConfig.orientation);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.w(TAG,"Configuration is " + " portrait" );
        }
    }*/
}
