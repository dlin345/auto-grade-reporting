package edu.cpp.cs585.auto_grade_reporting_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom Java annotation for automated grade reporting.
 *
 * @author delin
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Grade {

    boolean enabled() default true;

}
