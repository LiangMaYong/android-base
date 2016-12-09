package com.liangmayong.base.support.router.exception;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class ActivityNotRouteException extends NotRouteException {

    public ActivityNotRouteException(String pattern) {
        super("activity", pattern);
    }
}
