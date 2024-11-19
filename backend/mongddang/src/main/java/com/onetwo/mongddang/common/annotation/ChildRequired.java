package com.onetwo.mongddang.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메소드에 사용
@Retention(RetentionPolicy.RUNTIME) // 런타임에 사용
public @interface ChildRequired {
    String message() default "유저 role 은 child 여야합니다.";
}
