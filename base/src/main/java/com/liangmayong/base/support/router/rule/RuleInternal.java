package com.liangmayong.base.support.router.rule;

import android.content.Context;

import com.liangmayong.base.support.router.exception.NotRouteException;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by LiangMaYong on 2016/12/9.
 */
public class RuleInternal {

    private static RuleInternal sInstance;

    private HashMap<String, Rule> mRules;

    private RuleInternal() {
        mRules = new HashMap<>();
        initDefaultRouter();
    }

    private void initDefaultRouter() {
        ActivityRule activityRule = new ActivityRule();
        addRule(activityRule.getScheme(), activityRule);
        ReceiverRule receiverRule = new ReceiverRule();
        addRule(receiverRule.getScheme(), receiverRule);
        ServiceRule serviceRule = new ServiceRule();
        addRule(serviceRule.getScheme(), serviceRule);
    }

    /**
     * get
     *
     * @return
     */
    public static RuleInternal get() {
        if (sInstance == null) {
            synchronized (RuleInternal.class) {
                if (sInstance == null) {
                    sInstance = new RuleInternal();
                }
            }
        }

        return sInstance;
    }

    public final RuleInternal addRule(String scheme, Rule rule) {
        mRules.put(scheme, rule);
        return this;
    }

    private <T, V> Rule<T, V> getRule(String pattern) {
        HashMap<String, Rule> rules = mRules;
        Set<String> keySet = rules.keySet();
        Rule<T, V> rule = null;
        for (String scheme : keySet) {
            if (pattern.startsWith(scheme)) {
                rule = rules.get(scheme);
                break;
            }
        }
        return rule;
    }

    /**
     * router
     *
     * @param pattern pattern
     * @param klass   klass
     * @param <T>     t
     * @return i
     */
    public final <T> RuleInternal router(String pattern, Class<T> klass) {
        Rule<T, ?> rule = getRule(pattern);
        if (rule == null) {
            throw new NotRouteException("unknown", pattern);
        }
        rule.router(pattern, klass);
        return this;
    }

    /**
     * invoke
     *
     * @param ctx     ctx
     * @param pattern pattern
     * @param <V>     v
     * @return v
     */
    public final <V> V invoke(Context ctx, String pattern) {
        Rule<?, V> rule = getRule(pattern);
        if (rule == null) {
            throw new NotRouteException("unknown", pattern);
        }
        return rule.invoke(ctx, pattern);
    }
}
