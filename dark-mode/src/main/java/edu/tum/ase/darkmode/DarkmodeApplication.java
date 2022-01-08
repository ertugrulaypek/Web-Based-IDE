package edu.tum.ase.darkmode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DarkmodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarkmodeApplication.class, args);
	}

}