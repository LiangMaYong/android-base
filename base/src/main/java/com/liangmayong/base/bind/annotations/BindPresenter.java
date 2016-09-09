package com.liangmayong.base.bind.annotations;

import com.liangmayong.base.bind.Presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * BindPresenter
 *
 * @author LiangMaYong
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindPresenter {
    @SuppressWarnings("rawtypes") Class<? extends Presenter>[] value();
}
