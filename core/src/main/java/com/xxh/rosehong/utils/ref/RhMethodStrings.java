package com.xxh.rosehong.utils.ref;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/31 下午11:42
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RhMethodStrings {
    String[] value();
}
