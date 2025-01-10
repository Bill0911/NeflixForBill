package com.example.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class NetflixApplication {

	private final Environment environment;

	public NetflixApplication(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(NetflixApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logPort() {
		String port = environment.getProperty("local.server.port");
		System.out.println("Application is running on port: " + port);
	}
}
