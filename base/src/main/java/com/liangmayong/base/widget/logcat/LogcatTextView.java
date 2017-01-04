package com.liangmayong.base.widget.logcat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liangmayong.base.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by munix on 20/12/16.
 */

public class LogcatTextView extends ScrollView implements LogcatListener {

    private int verboseColor, debugColor, errorColor, infoColor, warningColor, consoleColor;
    private TextView textView;
    private boolean isBottom = true;
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

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = (View) getChildAt(getChildCount() - 1);
        int d = view.getBottom();
        d -= (getHeight() + getScrollY());
        if (d == 0) {
            isBottom = true;
        } else {
            isBottom = false;
            super.onScrollChanged(l, t, oldl, oldt);
        }
    }

    private void init(AttributeSet attrs) {
        textView = new TextView(getContext());
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        textView.setPadding(20, 20, 20, 20);
        addView(textView);

        textView.setTextColor(getContext().getResources().getColor(R.color.defaultTextColor));

        if (attrs != null) {
            TypedArray a = getContext().getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.LogcatTextView, 0, 0);

            try {
                verboseColor = a.getColor(R.styleable.LogcatTextView_verboseColor, getContext().getResources()
                        .getColor(R.color.defaultVerboseColor));
                debugColor = a.getColor(R.styleable.LogcatTextView_debugColor, getContext().getResources()
                        .getColor(R.color.defaultDebugColor));
                errorColor = a.getColor(R.styleable.LogcatTextView_errorColor, getContext().getResources()
                        .getColor(R.color.defaultErrorColor));
                infoColor = a.getColor(R.styleable.LogcatTextView_infoColor, getContext().getResources()
                        .getColor(R.color.defaultInfoColor));
                warningColor = a.getColor(R.styleable.LogcatTextView_warningColor, getContext().getResources()
                        .getColor(R.color.defaultWarningColor));

                consoleColor = a.getColor(R.styleable.LogcatTextView_consoleColor, getContext().getResources()
                        .getColor(R.color.defaultConsoleColor));
            } finally {
                a.recycle();
            }
        } else {
            verboseColor = getContext().getResources().getColor(R.color.defaultVerboseColor);
            debugColor = getContext().getResources().getColor(R.color.defaultDebugColor);
            errorColor = getContext().getResources().getColor(R.color.defaultErrorColor);
            infoColor = getContext().getResources().getColor(R.color.defaultInfoColor);
            warningColor = getContext().getResources().getColor(R.color.defaultWarningColor);
            consoleColor = getContext().getResources().getColor(R.color.defaultConsoleColor);
        }
        setBackgroundColor(consoleColor);
        textView.setBackgroundColor(consoleColor);
    }

    public void refreshLogcat() {
        getLogcat();
    }

    @Override
    public void onLogcatCaptured(String logcat) {
        textView.setText(Html.fromHtml(logcat));
        if (isBottom) {
            scrollToBottom();
        }
    }

    private void scrollToBottom() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    // handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onLogcatCaptured((String) msg.obj);
            if (!isAuto) {
                isAuto = true;
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
        new Thread() {
            @Override
            public void run() {
                try {
                    String[] command = new String[]{
                            "logcat",
                            "-c",
                    };
                    Runtime.getRuntime().exec(command);
                    refreshLogcat();
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public String getLogcatTag() {
        return logcatTag;
    }

    public void setLogcatTag(String logcatTag) {
        this.logcatTag = logcatTag;
    }

    /**
     * getLogcat
     */
    private void getLogcat() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String processId = Integer.toString(android.os.Process.myPid());
                    Process process = null;
                    String logcat_tag = getLogcatTag();
                    if (logcat_tag != null && !"".equals(logcat_tag)) {
                        process = Runtime.getRuntime().exec("logcat -d -v threadtime -s " + logcat_tag);
                    } else {
                        process = Runtime.getRuntime().exec("logcat -d -v threadtime");
                    }
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
                    handler.obtainMessage(0, log.toString()).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
