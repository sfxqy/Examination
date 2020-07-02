package cn.edu.hbuas.examsys.utils;



import cn.edu.hbuas.examsys.model.ResponseData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author SFX
 * 包装异常
 */
@Aspect
@Component
public class ControllerAOP {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    @Pointcut("execution(public cn.edu.hbuas.examsys.model.ResponseData *(..))")
    public void controllerMethod() {
    }

    @Around("controllerMethod()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        ResponseData result;
        result = (ResponseData) pjp.proceed();
        logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        return result;
    }

}
