package cn.mlick.aopservicedemo.annotation;

import java.lang.annotation.*;

/**
 * description
 * create by lixiangxin at 2018/11/29 10:37
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MarsValid {

    String value() default "";

}
