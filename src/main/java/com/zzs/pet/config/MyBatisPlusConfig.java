package com.zzs.pet.config;

import com.zzs.pet.interceptor.PasswordNullableResultSetHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wongbuer
 * @createDate 2024/5/22
 */
//@Configuration
public class MyBatisPlusConfig {
    @Bean
    public String passwordNullableResultSetHandler(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addInterceptor(new PasswordNullableResultSetHandler());
        return "passwordNullableResultSetHandler";
    }
}
