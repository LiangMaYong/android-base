package com.liangmayong.base.support.router.exception;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class ReceiverNotRouteException extends NotRouteException {

    public ReceiverNotRouteException(String pattern) {
        super("receiver", pattern);
    }
}
