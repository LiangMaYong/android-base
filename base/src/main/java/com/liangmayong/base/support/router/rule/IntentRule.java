package com.liangmayong.base.support.router.rule;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public abstract class IntentRule<T> implements Rule<T, Intent> {

    private HashMap<String, Class<T>> mIntentRules;

    public IntentRule() {
        mIntentRules = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void router(String pattern, Class<T> klass) {
        mIntentRules.put(pattern, klass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Intent invoke(Context ctx, String pattern) {
        Class<T> klass = mIntentRules.get(pattern);
        if (klass == null) {
            throwException(pattern);
        }
        return new Intent(ctx, klass);
    }

    public abstract void throwException(String pattern);

}