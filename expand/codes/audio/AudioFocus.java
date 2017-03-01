package com.liangmayong.base.support.audio;

import android.content.Context;
import android.media.AudioManager;

public class AudioFocus {

    private AudioManager audioManager;

    public AudioFocus(Context context) {
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public boolean requestFocus() {
        // Request audio focus for playback
        int result = audioManager.requestAudioFocus(focusChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    public boolean abandonAudioFocus() {
        // Request audio focus for playback
        int result = audioManager.abandonAudioFocus(focusChangeListener);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private OnAudioFocusChangeListener onAudioFocusChangeListener;

    private AudioManager.OnAudioFocusChangeListener focusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (onAudioFocusChangeListener != null) {
                onAudioFocusChangeListener.onAudioFocusChange(focusChange);
            }
        }
    };

    /**
     * setOnAudioFocusChangeListener
     *
     * @param onAudioFocusChangeListener onAudioFocusChangeListener
     */
    public void setOnAudioFocusChangeListener(OnAudioFocusChangeListener onAudioFocusChangeListener) {
        this.onAudioFocusChangeListener = onAudioFocusChangeListener;
    }

    public interface OnAudioFocusChangeListener extends AudioManager.OnAudioFocusChangeListener {
        @Override
        void onAudioFocusChange(int focusChange);
    }
}
