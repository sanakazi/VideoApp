package com.example.sanakazi.videoapp;

import android.app.Activity;
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
import java.util.TimerTask;


/**
 * Created by SanaKazi on 10/27/2017.
 */

public class VideoStream implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnBufferingUpdateListener {

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
    private static final String TAG = VideoStream.class.getSimpleName();


    public VideoStream(Context ctx) {
        Log.w(TAG, "CONSTRUCTOR ");
        this.ctx = ctx;

        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnBufferingUpdateListener(this);

     /*   PowerManager powerManager = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyMediaPlayer");*/
    }


    public void play(String VIDEO_PATH) {
        Log.w(TAG, " play()" + "isPlaying = " + VideoControlsActivity.isPlaying);
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
        updateMediaProgress();

    }


    public void playerResume() {
        if (mPlayer != null) {
            Log.w(TAG, "playerResume()");
            mPlayer.start();
            VideoControlsActivity.isPlaying = true;
        }
        updateMediaProgress();
    }


    /**
     * Release the video object.
     * This will stops the playback and release the memory used.
     */

    public void releaseMediaPlayer() {
        Log.w(TAG, "releaseMediaPlayer()");
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            VideoControlsActivity.isPlaying = true;
        }
    }

    /**
     * Pause the video playback.
     */
    public void pause() {
        Log.w(TAG, "pause() of Videostream");
        mPlayer.pause();
        VideoControlsActivity.isPlaying = false;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.w(TAG, "onPrepared()");
        mp.start();
        if (seekBar != null) {
            mp.setOnSeekCompleteListener(this);
            int duration = (int) mp.getDuration();
            seekBar.setMax(duration);
            VideoControlsActivity.tvDuration.setText(getDurationInSeconds(duration));


            System.out.println("PREPAREDDDD");


        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.w(TAG, "onCompletion()");

    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.w(TAG, "onBufferingUpdate() - " + percent);
        // seekBar.setSecondaryProgress(mPlayer.getCurrentPosition()+5);
        double ratio = percent / 100.0;
        int  bufferingLevel = (int)(mp.getDuration() * ratio);
        seekBar.setSecondaryProgress(bufferingLevel);
    }

    //region seekbar controls

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        VideoControlsActivity.tvStartTime.setText(getDurationInSeconds(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.w(TAG, "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.w(TAG, "onStopTrackingTouch");

        try {
            if (timer!=null) {
                timer.cancel();
                timer = null;
            }
            if (mPlayer!=null) {
                mPlayer.seekTo(seekBar.getProgress());
            }
            updateMediaProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * Sets up a seekbar and two labels to display the video progress.
     *
     * @param seekBar // * @param lblCurrentPosition
     *                //* @param lblDuration
     */
    public void setSeekBar(SeekBar seekBar/*, TextView lblCurrentPosition, TextView lblDuration*/) {


        this.seekBar = seekBar;
        // this.lblCurrentPosition = lblCurrentPosition;
        //  this.lblDuration = lblDuration;
        System.out.println("SETTING SEEK BAr");
        seekBar.setOnSeekBarChangeListener(this);

    }


    /**
     * Get a string with the video's duration.
     * The format of the string is hh:mm:ss
     *
     * @param sec - The seconds to convert.
     * @return A string formated.
     */
    private String getDurationInSeconds(int sec) {
        sec = sec / 1000;
        int hours = sec / 3600;
        int minutes = (sec / 60) - (hours * 60);
        int seconds = sec - (hours * 3600) - (minutes * 60);
        String formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return formatted;
    }


    /**
     * Update the seekbar while the video is playing.
     */
    public void updateMediaProgress() {
        timer = new Timer("progress Updater");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ((Activity) ctx).runOnUiThread(new Runnable() {
                    public void run() {
                        if (timer != null) {
                            try {
                                if (mPlayer != null) {
                                    if (VideoControlsActivity.isPlaying) {
                                        if (seekBar != null) {
                                            seekBar.setProgress(mPlayer.getCurrentPosition());

                                            VideoControlsActivity.tvStartTime.setText(getDurationInSeconds(mPlayer.getCurrentPosition()));
                                        }
                                    }

                                }
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
            }
        }, 0, 1000);
    }

    //endregion
}
