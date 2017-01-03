package com.liangmayong.base.support.audio;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.liangmayong.base.support.utils.ContextUtils;

import java.io.File;

public class AudioRecorder {

    /**
     * OnAudioRecorderListener
     */
    public interface OnAudioRecorderListener {
        void onError(Exception e);

        void onSuccess(String path, long size, long millis);

        void onRecording(double amplitude, double amplitudeEMA);
    }

    private static final double EMA_FILTER = 0.6;
    private MediaRecorder mRecorder = null;
    private double mEMA = 0.0;
    private String audioPath = "";
    private long startMillis = 0;
    private long audioMillis = 0;

    /**
     * HXIMAudioRecorder
     *
     * @param name name
     */
    public AudioRecorder(String name) {
        File file = new File(getTempPath(name));
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        audioPath = file.getPath();
    }

    private OnAudioRecorderListener audioRecorderListener;

    /**
     * setAudioRecorderListener
     *
     * @param audioRecorderListener audioRecorderListener
     */
    public void setAudioRecorderListener(OnAudioRecorderListener audioRecorderListener) {
        this.audioRecorderListener = audioRecorderListener;
    }

    /**
     * handler
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (audioRecorderListener != null) {
                    File file = new File(audioPath);
                    audioRecorderListener.onSuccess(audioPath, file.length(), audioMillis);
                }
            }
        }
    };

    @SuppressLint("InlinedApi")
    public void start() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (audioRecorderListener != null) {
                audioRecorderListener.onError(new Exception("Storage state if the media is present and mounted at its mount point with read/write access."));
            }
            return;
        }
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            if (profile.audioBitRate > 44100) {
                profile.audioBitRate = 44100;
            } else if (profile.audioBitRate < 12200 * 1.5) {
                profile.audioBitRate = (int) (12200 * 1.5);
            }
            if (profile.audioSampleRate < 8000 * 1.5) {
                profile.audioSampleRate = (int) (8000 * 1.5);
            }
            profile.audioChannels = 1;
            mRecorder.setAudioEncodingBitRate(profile.audioBitRate);
            mRecorder.setAudioSamplingRate(profile.audioSampleRate);
            mRecorder.setAudioChannels(profile.audioChannels);
            fileNotExistsCreate(audioPath);
            mRecorder.setOutputFile(audioPath);
            try {
                mRecorder.prepare();
                mRecorder.start();
                startMillis = System.currentTimeMillis();
                mEMA = 0.0;
                handler.postDelayed(amplitudeRun, 500);
            } catch (Exception e) {
                if (audioRecorderListener != null) {
                    audioRecorderListener.onError(e);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void stop(final boolean save) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRecorder != null) {
                    try {
                        mRecorder.setOnErrorListener(null);
                        mRecorder.setOnInfoListener(null);
                        mRecorder.setPreviewDisplay(null);
                        mRecorder.stop();
                        audioMillis = System.currentTimeMillis() - startMillis;
                    } catch (IllegalStateException e) {
                    } catch (RuntimeException e) {
                    } catch (Exception e) {
                    }
                    mRecorder.release();
                    mRecorder = null;
                    handler.removeCallbacks(amplitudeRun);
                    if (save) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(1);
                            }
                        });
                        thread.start();
                    }
                }
            }
        }, 200);
    }

    /**
     * getTempPath
     *
     * @param name name
     * @return path
     */
    private static String getTempPath(String name) {
        File file = ContextUtils.getApplication().getDir("audio_recorder", Context.MODE_PRIVATE);
        return new File(file.getPath() + "/" + name).getPath();
    }

    /**
     * fileNotExistsCreate
     *
     * @param path path
     * @return file
     */
    private final File fileNotExistsCreate(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /**
     * amplitudeRun
     */
    private Runnable amplitudeRun = new Runnable() {
        @Override
        public void run() {
            if (audioRecorderListener != null) {
                audioRecorderListener.onRecording(getAmplitude(), getAmplitudeEMA());
            }
            handler.postDelayed(this, 500);
        }
    };


    /**
     * getAmplitude
     *
     * @return amplitude
     */
    private double getAmplitude() {
        if (mRecorder != null)
            return (mRecorder.getMaxAmplitude() / 2700.0);
        else
            return 0;
    }

    /**
     * getAmplitudeEMA
     *
     * @return amplitude ema
     */
    private double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }
}
