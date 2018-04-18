package com.filter.textcorrector.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableAutoConfiguration()
@EnableResourceServer
public class SwearCheckerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwearCheckerApiApplication.class, args);
	}
}
