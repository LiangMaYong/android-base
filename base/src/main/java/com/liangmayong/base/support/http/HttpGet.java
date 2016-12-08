package com.liangmayong.base.support.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Get tool class
 *
 * @author LiangMaYong
 * @version 1.0
 */
class HttpGet {

    public static void get(String url, OnHttpListener netListener) {
        Get get = new Get(url);
        get.get(netListener);
    }

    /**
     * GET
     *
     * @param url         url
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, OnHttpListener netListener) {
        Get get = new Get(url, encode);
        get.get(netListener);
    }

    /**
     * GET
     *
     * @param url         url
     * @param cookie      cookie
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, String cookie, OnHttpListener netListener) {
        Get get = new Get(url, encode, cookie);
        get.get(netListener);
    }

    private static class Get {
        final int Success = 1;
        final int Error = 2;
        private OnHttpListener Listener;
        private String url, cookie, encode = "utf-8";

        public Get(String url, String encode) {
            this.encode = encode;
            this.url = url;
        }

        public Get(String url, String encode, String cookie) {
            this.encode = encode;
            this.url = url;
            this.cookie = cookie;
        }

        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Success:
                        if (Listener != null) {
                            try {
                                Listener.success((byte[]) msg.obj, encode, cookie);
                            } catch (Exception e) {
                            }
                        }
                        break;
                    case Error:
                        if (Listener != null) {
                            try {
                                Listener.error(new HttpError((Exception) msg.obj));
                            } catch (Exception e) {
                            }
                        }
                        break;
                }
            }

            ;
        };

        public Get(String url) {
            this.url = url;
        }

        public void get(OnHttpListener netListener) {
            this.Listener = netListener;
            HttpUtil.executorService().execute(new Runnable() {
                @Override
                public void run() {
                    int state = -1;
                    Object object = null;
                    HttpURLConnection localHttpURLConnection = null;
                    try {
                        localHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                        localHttpURLConnection.addRequestProperty("Cookie", cookie);
                        localHttpURLConnection.setConnectTimeout(5000);
                        localHttpURLConnection.setReadTimeout(5000);
                        localHttpURLConnection.setRequestMethod("GET");
                        localHttpURLConnection.setDoInput(true);
                        localHttpURLConnection.connect();
                        int code = 0;
                        code = localHttpURLConnection.getResponseCode();
                        if (code == 200) {
                            InputStream inputStream;
                            inputStream = localHttpURLConnection.getInputStream();
                            Map<String, List<String>> map = localHttpURLConnection.getHeaderFields();
                            String cookieVal = cookie;
                            for (String name : map.keySet()) {
                                if ("Set-Cookie".equals(name)) {
                                    cookieVal = map.get(name).toString().replace("[", "").replace("]", "");
                                    break;
                                } else if ("Content-Type".equals(name)) {
                                    int m = map.get(name).toString().indexOf("charset=");
                                    encode = map.get(name).toString().substring(m + 8).replace("]", "");
                                }
                            }
                            cookie = cookieVal;
                            object = inputStream2bytes(inputStream, encode);
                            state = Success;
                        } else {
                            object = new HttpServiceError(code);
                            state = Error;
                        }
                    } catch (MalformedURLException e1) {
                        object = e1;
                        state = Error;
                        try {
                            localHttpURLConnection.disconnect();
                        } catch (Exception e) {
                        }
                    } catch (SocketTimeoutException e1) {
                        object = e1;
                        state = Error;
                        try {
                            localHttpURLConnection.disconnect();
                        } catch (Exception e) {
                        }
                    } catch (UnsupportedEncodingException e1) {
                        object = e1;
                        state = Error;
                        try {
                            localHttpURLConnection.disconnect();
                        } catch (Exception e) {
                        }
                    } catch (IOException e1) {
                        object = e1;
                        state = Error;
                        try {
                            localHttpURLConnection.disconnect();
                        } catch (Exception e) {
                        }
                    }
                    Message msg = handler.obtainMessage();
                    msg.what = state;
                    msg.obj = object;
                    msg.sendToTarget();
                }
            });
        }
    }

    private static byte[] inputStream2bytes(InputStream stream, String encode) {
        ByteArrayOutputStream nBuilder = new ByteArrayOutputStream();
        try {
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = stream.read(data)) != -1) {
                nBuilder.write(data, 0, len);
            }
            nBuilder.close();
        } catch (IOException e1) {
        }
        return nBuilder.toByteArray();
    }
}
