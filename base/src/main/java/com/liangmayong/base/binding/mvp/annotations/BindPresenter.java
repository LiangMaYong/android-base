package com.liangmayong.base.binding.mvp.annotations;

import com.liangmayong.base.binding.mvp.Presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LiangMaYong on 2016/9/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindPresenter {

    @SuppressWarnings("rawtypes")
    Class<? extends Presenter>[] value();

}
