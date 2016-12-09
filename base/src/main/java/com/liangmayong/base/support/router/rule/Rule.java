package com.liangmayong.base.support.router.rule;

import android.content.Context;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public interface Rule<T, V> {

    void router(String pattern, Class<T> klass);

    V invoke(Context ctx, String pattern);

    String getScheme();
}
