package com.liangmayong.base.support.http;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HttpUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class HttpUtils {

    private HttpUtils() {
    }

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
    public static void post(String url, Map<String, String> params, OnHttpListener netListener) {
        HttpPost.post(url, params, null, null, netListener);
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
                            OnHttpListener netListener) {
        HttpPost.post(url, params, null, headers, netListener);
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
                            Map<String, HttpFile> files, OnHttpListener netListener) {
        HttpPost.post(url, params, files, headers, netListener);
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
                            Map<String, HttpFile> files, String encode, OnHttpListener netListener) {
        HttpPost.post(url, params, files, headers, encode, netListener);
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
                            Map<String, HttpFile> files, String encode, String cookie, OnHttpListener netListener) {
        HttpPost.post(url, params, files, headers, encode, cookie, netListener);
    }

    /**
     * doGet
     *
     * @param url         url
     * @param netListener callback listener
     */
    public static void get(String url, OnHttpListener netListener) {
        HttpGet.get(url, netListener);
    }

    /**
     * doGet
     *
     * @param url         url
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, OnHttpListener netListener) {
        HttpGet.get(url, encode, netListener);
    }

    /**
     * doGet
     *
     * @param url         url
     * @param cookie      cookie
     * @param encode      encode default = utf8
     * @param netListener callback listener
     */
    public static void get(String url, String encode, String cookie, OnHttpListener netListener) {
        HttpGet.get(url, encode, cookie, netListener);
    }
}
