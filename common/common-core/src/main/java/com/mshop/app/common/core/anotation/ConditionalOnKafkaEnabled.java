package com.mshop.app.common.core.anotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(value = "kafka.enabled")
public @interface ConditionalOnKafkaEnabled {

    @AliasFor(annotation = ConditionalOnProperty.class, attribute = "havingValue")
    String havingValue() default "true";

    @AliasFor(annotation = ConditionalOnProperty.class, attribute = "matchIfMissing")
    boolean matchIfMissing() default false;
}