package com.liangmayong.base.support.router.exception;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class ServiceNotRouteException extends NotRouteException {

    public ServiceNotRouteException(String pattern) {
        super("service", pattern);
    }
}
