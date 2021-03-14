package com.patient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.patient.web.PatientMvcController;

@SpringBootApplication
public class PaitentApplication {


	private static final Logger logger = LogManager.getLogger(PatientMvcController.class);
	public static void main(String[] args) {
		logger.info("Application getting Started.");
		SpringApplication.run(PaitentApplication.class, args);
		logger.info("Application Started.");
	}

}
