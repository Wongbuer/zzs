package com.zzs.pet;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zzs.pet.mapper")
@EnableFileStorage
public class ZzsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzsApplication.class, args);
    }

}
