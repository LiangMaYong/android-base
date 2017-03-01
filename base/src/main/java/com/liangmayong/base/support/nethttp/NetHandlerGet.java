package com.liangmayong.base.support.nethttp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * DoGet tool class
 *
 * @author LiangMaYong
 * @version 1.0
 */
class NetHandlerGet {

    public static void get(String url, OnNetHttpListener netListener) {
        DoGet get = new DoGet(url);
        get.doGet(netListener);
    }

    /**
     * GET
     *
     * @param url         url
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, OnNetHttpListener netListener) {
        DoGet get = new DoGet(url, encode);
        get.doGet(netListener);
    }

    /**
     * GET
     *
     * @param url         url
     * @param cookie      cookie
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, String cookie, OnNetHttpListener netListener) {
        DoGet get = new DoGet(url, encode, cookie);
        get.doGet(netListener);
    }

    /**
     * DoGet
     */
    private static class DoGet {

        final int Success = 1;
        final int Error = 2;
        private OnNetHttpListener Listener;
        private String url, cookie, encode = "utf-8";

        /**
         * DoGet
         *
         * @param url url
         */
        public DoGet(String url) {
            this.url = url;
        }

        /**
         * DoGet
         *
         * @param url    url
         * @param encode encode
         */
        public DoGet(String url, String encode) {
            this.encode = encode;
            this.url = url;
        }

        /**
         * DoGet
         *
         * @param url    url
         * @param encode encode
         * @param cookie cookie
         */
        public DoGet(String url, String encode, String cookie) {
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
                                Listener.error(new NetError((Exception) msg.obj));
                            } catch (Exception e) {
                            }
                        }
                        break;
                }
            }

            ;
        };

        /**
         * doGet
         *
         * @param netListener netListener
         */
        public void doGet(OnNetHttpListener netListener) {
            this.Listener = netListener;
            NetHttp.executorService().execute(new Runnable() {
                @Override
                public void run() {
                    int state = -1;
                    Object object = null;
                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) new URL(url).openConnection();
                        connection.addRequestProperty("Cookie", cookie);
                        connection.setConnectTimeout(5000);
                        connection.setReadTimeout(5000);
                        connection.setRequestMethod("GET");
                        connection.setDoInput(true);
                        connection.connect();
                        int code = 0;
                        code = connection.getResponseCode();
                        if (code == 200) {
                            InputStream inputStream;
                            inputStream = connection.getInputStream();
                            Map<String, List<String>> map = connection.getHeaderFields();
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
                            object = new NetServiceError(code);
                            state = Error;
                        }
                    } catch (Exception e1) {
                        object = e1;
                        state = Error;
                    } finally {
                        try {
                            connection.disconnect();
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

    /**
     * input stream to bytes
     *
     * @param stream stream
     * @param encode encode
     * @return bytes
     */
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
