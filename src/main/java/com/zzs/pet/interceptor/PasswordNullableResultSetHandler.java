package com.zzs.pet.interceptor;

import com.zzs.pet.domain.User;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Statement;
import java.util.List;

/**
 * @author Wongbuer
 * @createDate 2024/5/22
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class PasswordNullableResultSetHandler implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<?> result = (List<?>) invocation.proceed();
        result.forEach(item -> {
            if (item instanceof User) {
                ((User) item).setPassword(null);
            }
        });
        return result;
    }
}