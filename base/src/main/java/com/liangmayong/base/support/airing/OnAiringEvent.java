package com.liangmayong.base.support.airing;

/**
 * Created by LiangMaYong on 2016/9/30.
 */
public abstract class OnAiringEvent<T> implements OnAiringListener {

    public abstract void onEvent(T event, String action);

    @Override
    public void onAiring(AiringContent content) {
        T t = content.getEvent();
        if (t != null) {
            onEvent(t, content.getAction());
        }
    }

}
