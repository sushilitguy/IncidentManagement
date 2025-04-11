package com.demo.incidentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Represents starting class for app
 */
@SpringBootApplication
public class IncidentManagementApplication {

	/**
	 * Starts running app from here
	 * @param args Commandline Arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(IncidentManagementApplication.class, args);
	}

}
