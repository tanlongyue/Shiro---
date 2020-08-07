package com.shiro.Shiro_Demo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan(basePackages = "com.shiro.Shiro_Demo.dao")
@ComponentScan({"com.gz.gy.yueyouth","com.gz.gy.yueyouth.common"})
@ServletComponentScan
@Configuration
@EnableCaching
public class ShiroDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroDemoApplication.class, args);
	}

}
