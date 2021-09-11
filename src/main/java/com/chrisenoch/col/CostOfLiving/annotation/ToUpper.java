package com.chrisenoch.col.CostOfLiving.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates which method arguments should be converted to uppercase. {@link com.chrisenoch.col.CostOfLiving.aop.COLConverter} implements the logic for converting the
 * method arguments to uppercase.
 * @author chris
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ToUpper {
	
}
