package org.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@MapperScan(basePackages = "org.blog.mapper")
public class FblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FblogApplication.class, args);
    }

}
