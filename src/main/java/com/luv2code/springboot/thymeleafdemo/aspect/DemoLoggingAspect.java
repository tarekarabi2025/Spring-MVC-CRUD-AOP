package com.luv2code.springboot.thymeleafdemo.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    // setup logger
    private Logger myLogger =  Logger.getLogger(getClass().getName());

    // setup pointcut declaration
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..)) ")
    private void forControllerPackage(){}

    // do the same for service and dao package
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..)) ")
    private void forDaoPackage(){}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..)) ")
    private void forServicePackage(){}

    // let's combine these pointcut expression
    @Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
    private void forAppFlow(){}

    // add @Before Advice
    @Before("forAppFlow()")
    public void before(JoinPoint theJointPoint ){

        // display method we're calling
        String theMethod = theJointPoint.getSignature().toShortString();
        myLogger.info("========>>> in @Before advice calling method : "+theMethod);

        // display the arguments to the method

        // get the arguments
        Object[] args = theJointPoint.getArgs();

        // loop through and display args
        for(Object tempArg : args){
            myLogger.info("=====>> arguments : "+ tempArg);
        }

    }

    // add @AfterReturning advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJointPoint , Object theResult){

        // display method we are returning from
        String theMethod = theJointPoint.getSignature().toShortString();
        myLogger.info("========>>> in @AfterReturning advice from method : "+theMethod);

        // display data returned
        myLogger.info("======>> result : "+ theResult);

    }
}
