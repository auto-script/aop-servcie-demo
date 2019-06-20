package cn.mlick.aopservicedemo.valid;

import cn.mlick.aopservicedemo.annotation.MarsValid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lixiangxin at 2018/11/29 10:42
 **/
@Component
@Aspect
public class MarsValidAspect extends ApplicationObjectSupport {

    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    private final ExpressionHandler expressionHandler;

    @Autowired
    public MarsValidAspect(ExpressionHandler expressionHandler) {
        this.expressionHandler = expressionHandler;
    }

    @Before("@annotation(cn.mlick.aopservicedemo.annotation.MarsValid)")
    public void marsValid(JoinPoint point) throws Exception {
        Object target = point.getTarget();
        String methodName = point.getSignature().getName();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        Method m = target.getClass().getMethod(methodName, parameterTypes);

        String[] parameterNames = discoverer.getParameterNames(m);
        if (parameterNames == null || parameterNames.length == 0) {
            return;
        }

        Object[] args = point.getArgs();

        MarsValid marsValid = AnnotationUtils.getAnnotation(m, MarsValid.class);
        if (marsValid != null) {
            proceedMarsValid(parameterNames, args, marsValid);
        }


    }

    private void proceedMarsValid(String[] parameterNames, Object[] args, MarsValid marsValid) {
        MarsValidUtil.hasLength(marsValid.value(), "expression string is null.");
        expressionHandler.parseExpression(parameterNames, args, marsValid.value());
    }

}
