package com.piggymetrics.registary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistaryApplication.class, args);
	}

}
