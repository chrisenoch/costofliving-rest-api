package com.chrisenoch.col.CostOfLiving.aop;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.chrisenoch.col.CostOfLiving.annotation.ToUpper;

/**
 * Converts the arguments of method parameters annotated with {@link com.chrisenoch.col.CostOfLiving.annotation.ToUpper} to uppercase by way of AOP.
 * @author chris
 *
 */
@Aspect
@Component
public class COLConverter {
	

	//@Pointcut("execution(* com.apress.messaging.service.*Service.*(..,@com.apress.messaging.annotation.ToUpper (*),..))")
	
	@Pointcut("execution(* com.chrisenoch.col.CostOfLiving.service.*ServiceImpl.*(..,@com.chrisenoch.col.CostOfLiving.annotation.ToUpper (*),..))")
    public void methodPointcut() {} //Learn code: pointcut expression with custom annotation. Think it means 0 or more args followed by @toUpper arg followed by zero or more args.
	
	@Around("methodPointcut()")
	public Object codeAudit(ProceedingJoinPoint pjp) throws Throwable{
		Object[] args = pjp.getArgs();
		
		Parameter[]  parameters = ((MethodSignature) pjp.getSignature()).getMethod().getParameters();
		
		Object[] testArray = IntStream.range(0, args.length) //Goodexample Java 8 Learn code
		.mapToObj(index -> {
			if (parameters[index].isAnnotationPresent(ToUpper.class)) {
				System.out.println("Is present");
			} else {
				System.out.println("Is not present");
			}
			
			return (parameters[index].isAnnotationPresent(ToUpper.class)) ? 
				(new String(args[index].toString().toUpperCase())) : (args[index]);} )
		.toArray();
		
		Object test =  pjp.proceed(IntStream.range(0, args.length) //Good example Java 8 Learn code
				.mapToObj(index -> {
					if (parameters[index].isAnnotationPresent(ToUpper.class)) {
						System.out.println("Is present");
					} else {
						System.out.println("Is not present");
					}
					
					return (parameters[index].isAnnotationPresent(ToUpper.class)) ? 
						(new String(args[index].toString().toUpperCase())) : (args[index]);} )
				.toArray());
		
		return test;
	}
	
}


