package com.liangmayong.base.support.router.exception;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class NotRouteException extends RuntimeException {

    public NotRouteException(String name, String pattern) {
        super(String.format("%s cannot be resolved with pattern %s, have you declared it in your Router?", name, pattern));
    }
}
