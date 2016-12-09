package com.liangmayong.base.support.router.rule;

import android.content.BroadcastReceiver;

import com.liangmayong.base.support.router.Router;
import com.liangmayong.base.support.router.exception.ReceiverNotRouteException;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class ReceiverRule extends IntentRule<BroadcastReceiver> {

    @Override
    public void throwException(String pattern) {
        throw new ReceiverNotRouteException(pattern);
    }

    @Override
    public String getScheme() {
        return Router.RECEIVER_SCHEME;
    }
}
