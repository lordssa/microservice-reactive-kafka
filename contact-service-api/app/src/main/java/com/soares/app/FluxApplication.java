package com.soares.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@ComponentScan(basePackages = "com.soares")
public class FluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.soares.app.FluxApplication.class, args);
	}

}
