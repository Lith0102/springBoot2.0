package com.core;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
	String setting();
	public enum AuthorizeType{ALL,ONE}
	AuthorizeType type() default AuthorizeType.ALL;
}
