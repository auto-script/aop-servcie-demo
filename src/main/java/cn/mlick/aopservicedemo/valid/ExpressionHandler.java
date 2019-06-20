package cn.mlick.aopservicedemo.valid;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * @author create by lixiangxin at 2018/12/3 15:12
 **/
@Component
public class ExpressionHandler {

    private static ExpressionParser parser = new SpelExpressionParser();

    private static MarsValidComMethods defaultSpELMethod = new MarsValidComMethods();

    private final ApplicationContext ac;

    @Autowired
    public ExpressionHandler(ApplicationContext ac) {
        this.ac = ac;
    }

    public void parseExpression(String[] parameterNames, Object[] args, String expStr) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        expStr = initExpContext(expStr, context);
        addArgumentsAsVariables(context, parameterNames, args);

        Expression exp = parser.parseExpression(expStr);
        MarsValidUtil.notNull(exp, "Expression is null.");

        if (!evaluateAsBoolean(exp, context)) {
            MarsValidUtil.throwException(parserParameters(expStr) + " condition is not access");
        }
    }

    /**
     * TODO 后面要做解析出具体哪个参数没通过
     *
     * @param expStr 解析前的表达式
     * @return 解析后的表达式
     */
    private String parserParameters(String expStr) {
        return expStr.replaceAll("#", "");
    }

    private String initExpContext(String expStr, StandardEvaluationContext context) {
        String prefix = "@";

        if (expStr.startsWith(prefix)) {
            int ctIndex = getIndexByCode(1, '.', expStr);
            String containerName = expStr.substring(1, ctIndex);
            expStr = expStr.substring(ctIndex + 1);

            Object object = null;
            try {
                object = ac.getBean(containerName);
            } catch (NoSuchBeanDefinitionException e) {
                MarsValidUtil.throwException(e);
            }

            context.setRootObject(object);
        } else {//load default method
            context.setRootObject(defaultSpELMethod);
        }

        return expStr;
    }

    private boolean evaluateAsBoolean(Expression expr, EvaluationContext ctx) {
        Boolean result = expr.getValue(ctx, Boolean.class);
        MarsValidUtil.notNull(result, "Expression getResult is null.");
        return result;
    }

    private void addArgumentsAsVariables(StandardEvaluationContext context, String[] params, Object[] args) {
        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }
    }

    private int getIndexByCode(int i, char s, String expStr) {
        for (; i < expStr.length(); i++) {
            if (expStr.charAt(i) == s) {
                return i;
            }
        }
        return 0;
    }


}
