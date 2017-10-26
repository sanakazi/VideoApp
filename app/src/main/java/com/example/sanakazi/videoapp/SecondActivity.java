package com.example.sanakazi.videoapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = SecondActivity.class.getSimpleName();
    RelativeLayout rlBottomControls;
    SurfaceView frameVideo;
    String video_url = "http://classicmusic-9521.kxcdn.com/video/Tu%20hi%20hai%20aashiqui%20orignal%20song%20from%20movie.mp4";
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
        frameVideo = (SurfaceView) findViewById(R.id.surface_view);

    }

    private void click_events() {
        frameVideo.setOnClickListener(new View.OnClickListener() {
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
