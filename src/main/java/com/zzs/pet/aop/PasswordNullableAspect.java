package com.zzs.pet.aop;

import com.zzs.pet.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author Wongbuer
 * @createDate 2024/5/19
 */
@Aspect
@Component
public class PasswordNullableAspect {
    @Pointcut("execution(* com.zzs.pet.controller.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object after(ProceedingJoinPoint pjp) throws Throwable {
        // 执行目标方法
        Object result = pjp.proceed();

        // 如果结果是User对象或User对象的集合，处理password字段
        if (result instanceof HashMap) {
            HashMap<String, Object> map = (HashMap<String, Object>) result;
            Object user = map.get("user");
            if (user instanceof User) {
                ((User) user).setPassword(null);
            } else {
                user = map.get("userList");
            }
            if (user instanceof List) {
                List<User> users = (List<User>) user;
                for (User u : users) {
                    u.setPassword(null);
                }
            }
        }

        return result;
    }
}
