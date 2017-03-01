package com.liangmayong.base.support.nethttp;

/**
 * HTTP Callback Listener
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface OnNetHttpListener {
    /**
     * success
     *
     * @param data   data
     * @param encode encode
     * @param cookie cookie
     */
    void success(byte[] data, String encode, String cookie);

    /**
     * error
     *
     * @param e httperror
     */
    void error(NetError e);
}
