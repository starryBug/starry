package com.starry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.starry.domain.mapper")
public class StarryApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarryApplication.class, args);
	}
}
