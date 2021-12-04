package com.kbfina.management;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagementApplication {

	private static final Logger logger = LogManager.getLogger(ManagementApplication.class);
	public static void main(String[] args) {
		logger.debug("Start ManagementApplication");
		SpringApplication.run(ManagementApplication.class, args);
	}
	
}
