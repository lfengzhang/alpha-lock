package net.h2so.alphalock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description AlphaLockKey 参数级
 * @Auther mikicomo
 * @Date 2019-05-17 16:33
 */
@Target(value = {ElementType.PARAMETER, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AlphaLockKey {

    /**
     * key 值
     * @return
     */
    String value() default "";
}
