package com.liangmayong.base.support.router.rule;

import android.app.Activity;

import com.liangmayong.base.support.router.Router;
import com.liangmayong.base.support.router.exception.ActivityNotRouteException;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class ActivityRule extends IntentRule<Activity> {

    @Override
    public void throwException(String pattern) {
        throw new ActivityNotRouteException(pattern);
    }

    @Override
    public String getScheme() {
        return Router.ACTIVITY_SCHEME;
    }
}
