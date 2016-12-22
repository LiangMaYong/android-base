package com.liangmayong.base.support.audio;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;

/**
 * AudioPlayer
 */
public class AudioPlayer {


    /**
     * OnAudioPlayListener
     */
    public interface OnAudioPlayListener {

        void onPrepared();

        void onCompletion();

        void onError(Exception e);
    }

    public AudioPlayer() {
    }

    // player
    private MediaPlayer player;
    // isPlaying
    private boolean isPlaying = false;

    /**
     * isPlaying
     *
     * @return isPlaying
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * stopPlay
     */
    public void stopPlay() {
        isPlaying = false;
        if (player != null) {
            try {
                player.reset();
                player.release();
                player = null;
            } catch (Exception e) {
            }
        }
    }

    /**
     * startPlay
     *
     * @param path     path
     * @param listener listener
     */
    public void startPlay(String path, final OnAudioPlayListener listener) {
        if (isPlaying()) {
            stopPlay();
        }
        player = new MediaPlayer();
        isPlaying = true;
        try {
            player.setDataSource(path);
            player.prepareAsync();
            player.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (player != null) {
                        player.start();
                    }
                    if (listener != null) {
                        listener.onPrepared();
                    }
                }
            });
            player.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlay();
                    if (listener != null) {
                        listener.onCompletion();
                    }
                }
            });
        } catch (Exception e) {
            if (listener != null) {
                listener.onError(e);
            }
        }
    }
}
