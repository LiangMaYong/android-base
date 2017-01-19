package com.liangmayong.base.widget.logcat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liangmayong.base.R;
import com.liangmayong.base.support.utils.ThreadPoolUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by munix on 20/12/16.
 */

public class LogcatTextView extends ScrollView {

    private int verboseColor, debugColor, errorColor, infoColor, warningColor, consoleColor;
    private TextView textView;
    private boolean isAuto = false;
    private String logcatTag = "";

    public LogcatTextView(Context context) {
        super(context);
        init(null);
    }

    public LogcatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LogcatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LogcatTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        textView = new TextView(getContext());
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        textView.setPadding(20, 20, 20, 20);
        textView.setText("loading...");
        addView(textView);

        textView.setTextColor(getContext().getResources().getColor(R.color.base_logcat_defaultTextColor));

        if (attrs != null) {
            TypedArray a = getContext().getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.LogcatTextView, 0, 0);

            try {
                verboseColor = a.getColor(R.styleable.LogcatTextView_verboseColor, getContext().getResources()
                        .getColor(R.color.base_logcat_defaultVerboseColor));
                debugColor = a.getColor(R.styleable.LogcatTextView_debugColor, getContext().getResources()
                        .getColor(R.color.base_logcat_defaultDebugColor));
                errorColor = a.getColor(R.styleable.LogcatTextView_errorColor, getContext().getResources()
                        .getColor(R.color.base_logcat_defaultErrorColor));
                infoColor = a.getColor(R.styleable.LogcatTextView_infoColor, getContext().getResources()
                        .getColor(R.color.base_logcat_defaultInfoColor));
                warningColor = a.getColor(R.styleable.LogcatTextView_warningColor, getContext().getResources()
                        .getColor(R.color.base_logcat_defaultWarningColor));

                consoleColor = a.getColor(R.styleable.LogcatTextView_consoleColor, getContext().getResources()
                        .getColor(R.color.base_logcat_defaultConsoleColor));
            } finally {
                a.recycle();
            }
        } else {
            verboseColor = getContext().getResources().getColor(R.color.base_logcat_defaultVerboseColor);
            debugColor = getContext().getResources().getColor(R.color.base_logcat_defaultDebugColor);
            errorColor = getContext().getResources().getColor(R.color.base_logcat_defaultErrorColor);
            infoColor = getContext().getResources().getColor(R.color.base_logcat_defaultInfoColor);
            warningColor = getContext().getResources().getColor(R.color.base_logcat_defaultWarningColor);
            consoleColor = getContext().getResources().getColor(R.color.base_logcat_defaultConsoleColor);
        }
        setBackgroundColor(consoleColor);
        textView.setBackgroundColor(consoleColor);
    }

    public void refreshLogcat() {
        getLogcat();
    }

    private void onLogcatCaptured(String logcat) {
        if (textView != null) {
            textView.setText(Html.fromHtml(logcat));
        }
    }

    // handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onLogcatCaptured((String) msg.obj);
            if (!isAuto) {
                isAuto = true;
                handler.removeCallbacks(autoRefresh);
                handler.postDelayed(autoRefresh, 5000);
            }
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            refreshLogcat();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            handler.removeCallbacks(autoRefresh);
        }
    }

    // autoRefresh
    private Runnable autoRefresh = new Runnable() {
        @Override
        public void run() {
            refreshLogcat();
            isAuto = false;
        }
    };

    /**
     * clearLogcat
     */
    public void clearLogcat() {
        if (threadPoolUtils == null) {
            threadPoolUtils = new ThreadPoolUtils(ThreadPoolUtils.Type.SingleThread, 1);
        }
        threadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("logcat -c");
                    refreshLogcat();
                } catch (Exception e) {
                }
            }
        });
    }

    public String getLogcatTag() {
        return logcatTag;
    }

    public void setLogcatTag(String logcatTag) {
        this.logcatTag = logcatTag;
    }

    private ThreadPoolUtils threadPoolUtils = null;

    /**
     * getLogcat
     */
    private void getLogcat() {
        if (threadPoolUtils == null) {
            threadPoolUtils = new ThreadPoolUtils(ThreadPoolUtils.Type.SingleThread, 1);
        }
        threadPoolUtils.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    String processId = Integer.toString(android.os.Process.myPid());
                    Process process = null;
                    String logcat_tag = getLogcatTag();
                    String command = "logcat -d -v threadtime";
                    if (logcat_tag != null && !"".equals(logcat_tag)) {
                        command = "logcat -d -v threadtime -s " + logcat_tag;
                    }
                    process = Runtime.getRuntime().exec(command);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process
                            .getInputStream()));
                    StringBuilder log = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.contains(processId)) {
                            int lineColor = verboseColor;

                            if (line.contains(" I ")) {
                                lineColor = infoColor;
                            } else if (line.contains(" E ")) {
                                lineColor = errorColor;
                            } else if (line.contains(" D ")) {
                                lineColor = debugColor;
                            } else if (line.contains(" W ")) {
                                lineColor = warningColor;
                            }

                            log.append("<font color=\"#" + Integer.toHexString(lineColor)
                                    .toUpperCase()
                                    .substring(2) + "\">" + line + "</font><br><br>");
                        }
                    }
                    handler.obtainMessage(0, command + "<br><br>" + log.toString()).sendToTarget();
                } catch (Exception e) {
                }
            }
        }, 500, TimeUnit.MILLISECONDS);
    }
}
