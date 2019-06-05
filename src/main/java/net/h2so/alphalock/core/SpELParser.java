package net.h2so.alphalock.core;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description SpEl解析器
 * @Auther mikicomo
 * @Date 2019-06-04 18:40
 */
@Component
public class SpELParser {

    /**
     * 解析表达式的值
     *
     * @param alphaKeys
     * @param argNameValue
     * @return
     */
    public Map<String, Object> parseExpValue(String[] alphaKeys, Map<String, Object> argNameValue) {

        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext context = new StandardEvaluationContext();

        Map<String, Object> paseResult = new HashMap<>();

        /* 将参数加入SpEL表达式上下文 */
        for (String argName : argNameValue.keySet()) {
            context.setVariable(argName, argNameValue.get(argName));
        }

        for (String alphaKey : alphaKeys) {
            for (String argName : argNameValue.keySet()) {
                if (alphaKey.contains(argName)) {
                    paseResult.put(alphaKey, parser.parseExpression(alphaKey).getValue(context));
                    continue;
                }
            }
        }

        return paseResult;

    }

}
