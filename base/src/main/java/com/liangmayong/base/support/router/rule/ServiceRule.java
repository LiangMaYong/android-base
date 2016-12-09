package com.liangmayong.base.support.router.rule;

import android.app.Service;

import com.liangmayong.base.support.router.Router;
import com.liangmayong.base.support.router.exception.ServiceNotRouteException;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class ServiceRule extends IntentRule<Service> {
    @Override
    public void throwException(String pattern) {
        throw new ServiceNotRouteException(pattern);
    }

    @Override
    public String getScheme() {
        return Router.SERVICE_SCHEME;
    }
}
