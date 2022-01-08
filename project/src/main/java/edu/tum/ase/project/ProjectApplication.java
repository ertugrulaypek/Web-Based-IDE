package edu.tum.ase.project;

import edu.tum.ase.project.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.sql.DataSource;

@EnableDiscoveryClient
@EnableResourceServer
@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ProjectApplication.class);

	@Autowired
	DataSource dataSource;

	@Autowired
	ProjectService projectService;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
