package kr.co.concurrency.concurrencytest.aop.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckLock {
}
