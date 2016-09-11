package com.liangmayong.base.bind.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title
 *
 * @author LiangMaYong
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Title {
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

    String note() default "";
}
