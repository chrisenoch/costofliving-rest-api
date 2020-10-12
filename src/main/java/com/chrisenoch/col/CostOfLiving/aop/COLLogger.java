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

@Aspect
@Component
public class COLLogger {
	

	//@Pointcut("execution(* com.apress.messaging.service.*Service.*(..,@com.apress.messaging.annotation.ToUpper (*),..))")

	@Pointcut("execution(* com.chrisenoch.col.CostOfLiving.service.*Service*.*(..,@com.chrisenoch.col.CostOfLiving.annotation.ToUpper (*),..))")
    public void methodPointcut() {} //Learn code: pointcut expression with custom annotation. Think it means 0 or more args followed by @toUpper arg followed by zero or more args.
	
	@Around("methodPointcut()")
	public Object codeAudit(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("Inside around advice");
		Object[] args = pjp.getArgs();
		System.out.println("Print args below");
		System.out.println(Arrays.toString(args));
		
		System.out.println("Inside around advice 2");
		Parameter[]  parameters = ((MethodSignature) pjp.getSignature()).getMethod().getParameters();
		System.out.println("Inside around advice 3");
		
		Object[] testArray = IntStream.range(0, args.length) //Fantastic example Java 8 Learn code
		.mapToObj(index -> {
			if (parameters[index].isAnnotationPresent(ToUpper.class)) {
				System.out.println("Is present");
			} else {
				System.out.println("Is not present");
			}
			
			return (parameters[index].isAnnotationPresent(ToUpper.class)) ? 
				(new String(args[index].toString().toUpperCase())) : (args[index]);} )
		.toArray();
		
		System.out.println("Test parameters CostOfLiving1: " + Arrays.deepToString((Object[]) testArray));
		
		Object test =  pjp.proceed(IntStream.range(0, args.length) //Fantastic example Java 8 Learn code
				.mapToObj(index -> {
					if (parameters[index].isAnnotationPresent(ToUpper.class)) {
						System.out.println("Is present");
					} else {
						System.out.println("Is not present");
					}
					
					return (parameters[index].isAnnotationPresent(ToUpper.class)) ? 
						(new String(args[index].toString().toUpperCase())) : (args[index]);} )
				.toArray());
		
		System.out.println("Test parameters CostOfLiving2: " );
		return test;
	}
	
}


