package com.example.sanakazi.videoapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;


/**
 * Created by SanaKazi on 10/27/2017.
 */

public class VideoStream implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnSeekCompleteListener,SeekBar.OnSeekBarChangeListener,MediaPlayer.OnBufferingUpdateListener {

    private int STATUS = 0;
    private final int STATUS_STOPED = 1;
    private final int STATUS_PLAYING = 2;
    private final int STATUS_PAUSED = 3;

    private Context ctx;
    private PowerManager.WakeLock wakeLock;
    public MediaPlayer mPlayer;
    private SeekBar seekBar = null;
    private SurfaceView surfaceView;
    //  private TextView lblCurrentPosition = null;
    // private TextView lblDuration = null;
    public static Timer timer = null;
    /*boolean isCompleted=false;*/
    private static final String TAG= VideoStream.class.getSimpleName();


    public VideoStream(Context ctx) {
        Log.w(TAG,"CONSTRUCTOR ");
        this.ctx = ctx;

        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnBufferingUpdateListener(this);

        PowerManager powerManager = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyMediaPlayer");
    }



    public void play(String VIDEO_PATH )
    {
        Log.w(TAG," play()" + "isPlaying = " + VideoControlsActivity.isPlaying);
            mPlayer.setDisplay(VideoControlsActivity.mSurfaceHolder);
            try {
                mPlayer.setDataSource(VIDEO_PATH);
                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(this);
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                VideoControlsActivity.isPlaying = true;

            } catch (IOException e) {
                e.printStackTrace();
            }

    }


    public void playerResume() {
        if (mPlayer != null){
            Log.w(TAG, "playerResume()");
        mPlayer.start();
            VideoControlsActivity.isPlaying = true;
    }
    else
    {

    }
    }


    /**
     * Release the video object.
     * This will stops the playback and release the memory used.
     */

    public void releaseMediaPlayer() {
        Log.w(TAG,"releaseMediaPlayer()");
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            VideoControlsActivity.isPlaying=true;
        }
    }

    /**
     * Pause the video playback.
     */
    public void pause() {
        Log.w(TAG,"pause() of Videostream");
        mPlayer.pause();
        VideoControlsActivity.isPlaying = false;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.w(TAG,"onPrepared()");
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.w(TAG,"onCompletion()");

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }
}
