package com.liangmayong.base.compat.binding.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BindTitle
 *
 * @author LiangMaYong
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindTitle {
    /**
     * value
     *
     * @return title
     */
    String value();
    /**
     * id
     *
     * @return titleId
     */
    int id() default 0;
}
