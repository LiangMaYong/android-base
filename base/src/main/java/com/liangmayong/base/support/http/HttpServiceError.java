package com.liangmayong.base.support.http;

/**
 * HttpServiceError
 *
 * @author LiangMaYong
 * @version 1.0
 */
class HttpServiceError extends Exception {
    private static final long serialVersionUID = 1L;

    int code = 0;

    public HttpServiceError(int code) {
        super("status code:" + code);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
