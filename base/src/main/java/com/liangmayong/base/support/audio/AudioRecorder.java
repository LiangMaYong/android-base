package com.liangmayong.base.support.audio;

import android.annotation.TargetApi;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;

public class AudioRecorder {

    /**
     * OnAudioRecorderListener
     */
    public interface OnAudioRecorderListener {

        void onError(Exception e);

        void onSuccess(String path, long millis);

        void onRecording(double amplitude, double amplitudeEMA);
    }

    private static final double EMA_FILTER = 0.6;
    private MediaRecorder mRecorder = null;
    private double mEMA = 0.0;
    private String audioPath = "";
    private long startMillis = 0;
    private long audioMillis = 0;
    private String audioDir = "";

    /**
     * AudioRecorder
     *
     * @param name name
     */
    public AudioRecorder(String name) {
        audioDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/android_base/audio_record";
        File file = new File(audioDir);
        if (!file.exists()) {
            file.mkdir();
        }
        audioPath = getAudioPath(name);
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (audioRecorderListener != null)
                    audioRecorderListener.onSuccess(audioPath, audioMillis);
            }
        }
    };

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
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            fileNotExistsCreate(audioPath);
            mRecorder.setOutputFile(audioPath);
            try {
                mRecorder.prepare();
                startMillis = System.currentTimeMillis();
                mRecorder.start();
                mEMA = 0.0;
                handler.postDelayed(amplitudeRun, 500);
            } catch (Exception e) {
                e.printStackTrace();
                if (audioRecorderListener != null) {
                    audioRecorderListener.onError(e);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void stop(boolean save) {
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

    /**
     * getAudioPath
     *
     * @param name name
     * @return path
     */
    private String getAudioPath(String name) {
        return audioDir + "/audio_" + name + ".aac";
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
