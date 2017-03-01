package com.liangmayong.base.support.nethttp;

/**
 * HttpServiceError
 *
 * @author LiangMaYong
 * @version 1.0
 */
class NetServiceError extends Exception {
    private static final long serialVersionUID = 1L;

    int code = 0;

    public NetServiceError(int code) {
        super("status code:" + code);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
