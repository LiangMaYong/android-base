package com.liangmayong.base.support.nethttp;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NetHttp
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class NetHttp {

    private NetHttp() {
    }

    public static int connect_time_out = 5000;
    public static int read_out_time = 5000;
    //executorService
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * executorService
     *
     * @return executorService
     */
    public static ExecutorService executorService() {
        return executorService;
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param netListener callback listener
     */
    public static void post(String url, Map<String, String> params, OnNetHttpListener netListener) {
        NetHandlerPost.post(url, params, null, null, netListener);
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param headers     headers
     * @param netListener callback listener
     */
    public static void post(String url, Map<String, String> params, Map<String, String> headers,
                            OnNetHttpListener netListener) {
        NetHandlerPost.post(url, params, null, headers, netListener);
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param headers     headers
     * @param files       files
     * @param netListener callback listener
     */
    public static void post(String url, Map<String, String> params, Map<String, String> headers,
                            Map<String, NetFile> files, OnNetHttpListener netListener) {
        NetHandlerPost.post(url, params, files, headers, netListener);
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param headers     headers
     * @param files       files
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void post(String url, Map<String, String> params, Map<String, String> headers,
                            Map<String, NetFile> files, String encode, OnNetHttpListener netListener) {
        NetHandlerPost.post(url, params, files, headers, encode, netListener);
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param headers     headers
     * @param files       files
     * @param encode      encode default = utf8
     * @param cookie      cookie
     * @param netListener callback listener
     */
    public static void post(String url, Map<String, String> params, Map<String, String> headers,
                            Map<String, NetFile> files, String encode, String cookie, OnNetHttpListener netListener) {
        NetHandlerPost.post(url, params, files, headers, encode, cookie, netListener);
    }

    /**
     * doGet
     *
     * @param url         url
     * @param netListener callback listener
     */
    public static void get(String url, OnNetHttpListener netListener) {
        NetHandlerGet.get(url, netListener);
    }

    /**
     * doGet
     *
     * @param url         url
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, OnNetHttpListener netListener) {
        NetHandlerGet.get(url, encode, netListener);
    }

    /**
     * doGet
     *
     * @param url         url
     * @param cookie      cookie
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, String cookie, OnNetHttpListener netListener) {
        NetHandlerGet.get(url, encode, cookie, netListener);
    }
}
