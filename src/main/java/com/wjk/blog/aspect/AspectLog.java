package com.wjk.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class AspectLog {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.wjk.blog.web.*.*(..))")
    public void log(){}
    @Before("log()")

    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();//获取HttpServletRequest对象
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr().toLowerCase();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+","+joinPoint.getSignature().getName();//获取类名与方法名
        Object[] args=joinPoint.getArgs();//获取参数
        MyLog myLog=new MyLog(url,ip,classMethod,args);
        System.out.println(myLog);
        logger.info("request:"+myLog);
    }
    @After("log()")
    public void doAfter(){
    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturning(Object[] result){
        logger.info("result {}"+result);
    }
    private class MyLog{
        String url;
        String ip;
        String classMethod;
        Object[] args;

        public MyLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
