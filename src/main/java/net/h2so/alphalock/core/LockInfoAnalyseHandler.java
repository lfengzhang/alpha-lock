package net.h2so.alphalock.core;

import net.h2so.alphalock.annotation.AlphaLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 锁信息分析处理类
 * @Auther mikicomo
 * @Date 2019-05-31 17:02
 */
public class LockInfoAnalyseHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获得锁的KeyName
     * 格式: 全类名.方法名-{kValue1}-{kValue2}
     * @param joinPoint
     * @param alphaLock
     * @return
     */
    public String getLockKeyName(ProceedingJoinPoint joinPoint, AlphaLock alphaLock) {

        Method method = getMethod(joinPoint);

        /*获得完整类名参数key定义*/
        String lockKeyName = getCompleteDefinitionAndKey(alphaLock.keys(), method);

        /*替换Key值为业务值*/
        lockKeyName = replaceKeyWithBusinessValue(lockKeyName, alphaLock.keys(), method, joinPoint.getArgs());

        return lockKeyName;
    }

    /**
     * 获得方法
     *
     * @param joinPoint
     * @return
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.getDeclaringClass().isInterface()) {
            /**
             * if method is interface, find the tagert and get Real Target Method
             */
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(),
                        method.getParameterTypes());
            } catch (Exception e) {
                logger.error("get interface method error ... msg:[{}]", e.getCause());
                e.printStackTrace();
            }
        }
        return method;
    }

    /**
     * 获取完整定义
     * 例: net.h2so.alphalock.core.LockInfoAnalyseHandler.getCompleteDefinition-{key1}-{key2}
     * @param
     * @param method
     * @return
     */
    private String getCompleteDefinitionAndKey(String[] alphaKeys, Method method){
        return null;
    }

    /**
     * 将key替换为业务值
     * @param alphaKeys
     * @param method
     * @return
     */
    private String replaceKeyWithBusinessValue(String completeDefinition, String[] alphaKeys, Method method, Object[] args) {

        return completeDefinition;
    }
}
